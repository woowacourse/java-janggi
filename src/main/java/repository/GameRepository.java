package repository;

import dao.BoardPieceDao;
import dao.BoardPieceRawData;
import dao.GameRoomDao;
import dao.GameRoomRawData;
import dao.MoveLogDao;
import dao.MoveLogRawData;
import db.ConnectionManager;
import db.PieceTypeMapper;
import domain.board.Board;
import domain.board.BoardMove;
import domain.board.BoardSnapshot;
import domain.game.GameStatus;
import domain.game.JanggiGame;
import domain.game.Team;
import domain.game.Turn;
import domain.game.progress.GameProgress;
import domain.game.progress.GameRecord;
import domain.game.progress.MoveLog;
import domain.game.progress.PassStreak;
import domain.piece.Piece;
import domain.position.Position;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GameRepository {
    private final ConnectionManager connectionManager;
    private final GameRoomDao gameRoomDao;
    private final BoardPieceDao boardPieceDao;
    private final MoveLogDao moveLogDao;

    public GameRepository(ConnectionManager connectionManager, GameRoomDao gameRoomDao,
                          BoardPieceDao boardPieceDao, MoveLogDao moveLogDao) {
        this.connectionManager = connectionManager;
        this.gameRoomDao = gameRoomDao;
        this.boardPieceDao = boardPieceDao;
        this.moveLogDao = moveLogDao;
    }

    public StoredGame createGame(String roomName, JanggiGame game) {
        return connectionManager.inTransaction(connection -> {
            long roomId = gameRoomDao.save(connection, toRawData(0L, roomName, game));
            boardPieceDao.saveAll(connection, roomId, toRawPieces(game.boardSnapshot()));
            return new StoredGame(roomId, game);
        });
    }

    public Optional<StoredGame> loadGame(long roomId) {
        return connectionManager.inTransaction(connection ->
                gameRoomDao.findById(connection, roomId).map(room -> assemble(connection, room))
        );
    }

    private StoredGame assemble(java.sql.Connection connection, GameRoomRawData roomData) {
        List<BoardPieceRawData> pieceData = boardPieceDao.findByGameRoomId(connection, roomData.id());
        Board board = toBoard(pieceData);
        Turn turn = Turn.of(Team.valueOf(roomData.currentTurn()));
        GameStatus status = GameStatus.valueOf(roomData.status());
        PassStreak passStreak = new PassStreak(roomData.consecutivePassCount());
        List<MoveLog> history = toHistory(moveLogDao.findByGameRoomId(connection, roomData.id()));
        GameProgress progress = GameProgress.restore(turn, status, new GameRecord(history), passStreak);
        return new StoredGame(roomData.id(), JanggiGame.restore(board, progress));
    }

    public void applyMove(StoredGame stored, BoardMove move) {
        long roomId = stored.id();
        JanggiGame game = stored.game();
        connectionManager.inTransaction(connection -> {
            gameRoomDao.update(connection, toRawData(roomId, "", game));
            Position source = move.source();
            Position destination = move.destination();
            boardPieceDao.deletePieceAt(connection, roomId, new BoardPieceRawData(source.row(), source.column(), "", ""));
            boardPieceDao.deletePieceAt(connection, roomId, new BoardPieceRawData(destination.row(), destination.column(), "", ""));
            boardPieceDao.insertPiece(connection, roomId, toRawPiece(destination, move.movedPiece()));
            moveLogDao.insert(connection, roomId, toLastLogRawData(game));
            return null;
        });
    }

    public void applyPass(StoredGame stored) {
        long roomId = stored.id();
        JanggiGame game = stored.game();
        connectionManager.inTransaction(connection -> {
            gameRoomDao.update(connection, toRawData(roomId, "", game));
            moveLogDao.insert(connection, roomId, toLastLogRawData(game));
            return null;
        });
    }

    public List<GameRoomSummary> listRooms() {
        return connectionManager.inTransaction(connection ->
                gameRoomDao.findAll(connection).stream()
                        .map(this::toSummary)
                        .toList()
        );
    }

    private GameRoomSummary toSummary(GameRoomRawData raw) {
        return new GameRoomSummary(
                raw.id(),
                raw.name(),
                Team.valueOf(raw.currentTurn()),
                GameStatus.valueOf(raw.status())
        );
    }

    private List<BoardPieceRawData> toRawPieces(BoardSnapshot board) {
        return Position.allPositions().stream()
                .filter(position -> board.pieceAt(position).isNotEmpty())
                .map(position -> toRawPiece(position, board.pieceAt(position)))
                .toList();
    }

    private BoardPieceRawData toRawPiece(Position position, Piece piece) {
        return new BoardPieceRawData(
                position.row(),
                position.column(),
                PieceTypeMapper.toTypeName(piece),
                piece.getTeam().name()
        );
    }

    private Board toBoard(List<BoardPieceRawData> pieceData) {
        Map<Position, Piece> pieces = new HashMap<>();
        for (BoardPieceRawData data : pieceData) {
            Position position = new Position(data.rowPos(), data.colPos());
            Piece piece = PieceTypeMapper.toPiece(data.pieceType(), Team.valueOf(data.team()));
            pieces.put(position, piece);
        }
        return new Board(pieces);
    }

    private GameRoomRawData toRawData(long roomId, String roomName, JanggiGame game) {
        return new GameRoomRawData(
                roomId,
                roomName,
                game.currentTurn().name(),
                game.getStatus().name(),
                game.getProgress().consecutivePassCount()
        );
    }

    private MoveLogRawData toLastLogRawData(JanggiGame game) {
        List<MoveLog> history = game.getProgress().history();
        int seq = history.size() - 1;
        MoveLog last = history.get(seq);
        return toRawLog(seq, last);
    }

    private MoveLogRawData toRawLog(int seq, MoveLog log) {
        if (log.isPass()) {
            return new MoveLogRawData(seq, log.type().name(), log.turn().name(),
                    null, null, null, null, null);
        }
        return new MoveLogRawData(
                seq,
                log.type().name(),
                log.turn().name(),
                log.source().row(),
                log.source().column(),
                log.destination().row(),
                log.destination().column(),
                PieceTypeMapper.toTypeName(log.piece())
        );
    }

    private List<MoveLog> toHistory(List<MoveLogRawData> rawLogs) {
        List<MoveLog> logs = new ArrayList<>();
        for (MoveLogRawData raw : rawLogs) {
            logs.add(toDomainLog(raw));
        }
        return logs;
    }

    private MoveLog toDomainLog(MoveLogRawData raw) {
        Team turn = Team.valueOf(raw.turn());
        MoveLog.Type type = MoveLog.Type.valueOf(raw.type());
        if (type == MoveLog.Type.PASS) {
            return new MoveLog(type, turn, null, null, null);
        }
        Position source = new Position(raw.fromRow(), raw.fromCol());
        Position destination = new Position(raw.toRow(), raw.toCol());
        Piece piece = PieceTypeMapper.toPiece(raw.pieceType(), turn);
        return new MoveLog(type, turn, source, destination, piece);
    }
}

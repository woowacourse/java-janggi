package repository;

import dao.BoardPieceDao;
import dao.BoardPieceRawData;
import dao.GameRoomDao;
import dao.GameRoomRawData;
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
import domain.game.progress.PassStreak;
import domain.piece.Piece;
import domain.position.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GameRepository {
    private final ConnectionManager connectionManager;
    private final GameRoomDao gameRoomDao;
    private final BoardPieceDao boardPieceDao;

    public GameRepository(ConnectionManager connectionManager, GameRoomDao gameRoomDao, BoardPieceDao boardPieceDao) {
        this.connectionManager = connectionManager;
        this.gameRoomDao = gameRoomDao;
        this.boardPieceDao = boardPieceDao;
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
        GameProgress progress = GameProgress.restore(turn, status, new GameRecord(), passStreak);
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
            return null;
        });
    }

    public void applyPass(StoredGame stored) {
        long roomId = stored.id();
        JanggiGame game = stored.game();
        connectionManager.inTransaction(connection -> {
            gameRoomDao.update(connection, toRawData(roomId, "", game));
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
}

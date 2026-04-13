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
import domain.game.GameRecord;
import domain.game.GameStatus;
import domain.game.JanggiGame;
import domain.game.Team;
import domain.game.Turn;
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
            long roomId = gameRoomDao.save(
                    connection,
                    roomName,
                    game.currentTurn().name(),
                    game.getStatus().name(),
                    game.getRecord().consecutivePassCount()
            );
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
        GameRecord record = new GameRecord(roomData.consecutivePassCount());
        GameStatus status = GameStatus.valueOf(roomData.status());
        return new StoredGame(roomData.id(), JanggiGame.restore(turn, board, record, status));
    }

    public void applyMove(StoredGame stored, BoardMove move) {
        long roomId = stored.id();
        JanggiGame game = stored.game();
        connectionManager.inTransaction(connection -> {
            gameRoomDao.update(
                    connection,
                    roomId,
                    game.currentTurn().name(),
                    game.getStatus().name(),
                    game.getRecord().consecutivePassCount()
            );
            Position source = move.source();
            Position destination = move.destination();
            boardPieceDao.deletePieceAt(connection, roomId, source.row(), source.column());
            boardPieceDao.deletePieceAt(connection, roomId, destination.row(), destination.column());
            boardPieceDao.insertPiece(connection, roomId, toRawPiece(destination, move.movedPiece()));
            return null;
        });
    }

    public void applyPass(StoredGame stored) {
        long roomId = stored.id();
        JanggiGame game = stored.game();
        connectionManager.inTransaction(connection -> {
            gameRoomDao.update(
                    connection,
                    roomId,
                    game.currentTurn().name(),
                    game.getStatus().name(),
                    game.getRecord().consecutivePassCount()
            );
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
}

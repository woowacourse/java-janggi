package repository;

import dao.BoardPieceDao;
import dao.BoardPieceRawData;
import dao.GameRoomDao;
import dao.GameRoomRawData;
import dao.MoveLogDao;
import db.ConnectionManager;
import domain.board.Board;
import domain.board.BoardMove;
import domain.game.JanggiGame;
import domain.game.Team;
import domain.game.Turn;
import domain.game.GameStatus;
import domain.game.progress.GameProgress;
import domain.game.progress.GameRecord;
import domain.game.progress.MoveLog;
import domain.game.progress.PassStreak;
import domain.position.Position;
import java.util.List;
import java.util.Optional;
import repository.mapper.BoardPieceMapper;
import repository.mapper.GameRoomMapper;
import repository.mapper.MoveLogMapper;

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
            long roomId = gameRoomDao.save(connection, GameRoomMapper.toRawData(0L, roomName, game));
            boardPieceDao.saveAll(connection, roomId, BoardPieceMapper.toRawPieces(game.boardSnapshot()));
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
        Board board = BoardPieceMapper.toBoard(pieceData);
        Turn turn = Turn.of(Team.valueOf(roomData.currentTurn()));
        GameStatus status = GameStatus.valueOf(roomData.status());
        PassStreak passStreak = new PassStreak(roomData.consecutivePassCount());
        List<MoveLog> history = MoveLogMapper.toHistory(moveLogDao.findByGameRoomId(connection, roomData.id()));
        GameProgress progress = GameProgress.restore(turn, status, new GameRecord(history), passStreak);
        return new StoredGame(roomData.id(), JanggiGame.restore(board, progress));
    }

    public void applyMove(StoredGame stored, BoardMove move) {
        long roomId = stored.id();
        JanggiGame game = stored.game();
        connectionManager.inTransaction(connection -> {
            gameRoomDao.update(connection, GameRoomMapper.toRawData(roomId, "", game));
            Position source = move.source();
            Position destination = move.destination();
            boardPieceDao.deletePieceAt(connection, roomId, new BoardPieceRawData(source.row(), source.column(), "", ""));
            boardPieceDao.deletePieceAt(connection, roomId, new BoardPieceRawData(destination.row(), destination.column(), "", ""));
            boardPieceDao.insertPiece(connection, roomId, BoardPieceMapper.toRawPiece(destination, move.movedPiece()));
            moveLogDao.insert(connection, roomId, MoveLogMapper.toLastLogRawData(game));
            return null;
        });
    }

    public void applyPass(StoredGame stored) {
        long roomId = stored.id();
        JanggiGame game = stored.game();
        connectionManager.inTransaction(connection -> {
            gameRoomDao.update(connection, GameRoomMapper.toRawData(roomId, "", game));
            moveLogDao.insert(connection, roomId, MoveLogMapper.toLastLogRawData(game));
            return null;
        });
    }

    public List<GameRoomSummary> listRooms() {
        return connectionManager.inTransaction(connection ->
                gameRoomDao.findAll(connection).stream()
                        .map(GameRoomMapper::toSummary)
                        .toList()
        );
    }
}

package service;

import infra.ConnectionManager;
import infra.DBExecutor;
import java.sql.Connection;
import domain.place.Place;
import domain.place.piece.Side;
import domain.position.Position;
import entity.GameRoomEntity;
import entity.GameStateEntity;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import repository.BoardRepository;
import repository.GameRoomRepository;
import repository.GameStateRepository;

public class GameService {

    private final BoardRepository boardRepository;
    private final GameRoomRepository gameRoomRepository;
    private final GameStateRepository gameStateRepository;
    private final DBExecutor dbExecutor;

    public GameService(BoardRepository boardRepository,
                       GameRoomRepository gameRoomRepository,
                       GameStateRepository gameStateRepository,
                       DBExecutor dbExecutor) {
        this.boardRepository = boardRepository;
        this.gameRoomRepository = gameRoomRepository;
        this.gameStateRepository = gameStateRepository;
        this.dbExecutor = dbExecutor;
    }

    public void saveGame(Map<Position, Place> board, String name, Side side) {
        dbExecutor.transaction(connection -> {
            long roomId = gameRoomRepository.save(name, connection);
            boardRepository.saveBoard(roomId, board, connection);
            gameStateRepository.save(roomId, side, connection);
        });
    }

    public Map<Position, Place> findBoardByRoomId(long roomId) {
        return dbExecutor.transaction(connection -> {
            existsById(roomId, connection);
            return boardRepository.findBoard(roomId, connection);
        });
    }

    public GameStateEntity findGameStateByRoomId(long roomId) {
        return dbExecutor.transaction(connection -> {
            existsById(roomId, connection);
            return gameStateRepository.findByRoomId(roomId, connection);
        });

    }

    public List<GameRoomEntity> findGameRoomAll() {
            return dbExecutor.transaction(gameRoomRepository::findAll);
    }

    private void existsById(long roomId, Connection conn) {
        if (!gameRoomRepository.existsById(roomId, conn)) {
            throw new IllegalArgumentException("[ERROR] 없는 방 번호입니다.");
        }
    }

}

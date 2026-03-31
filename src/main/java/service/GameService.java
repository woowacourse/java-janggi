package service;

import config.H2ConnectionManager;
import domain.place.Place;
import domain.place.piece.Side;
import domain.position.Position;
import entity.GameRoomEntity;
import entity.GameStateEntity;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import repository.BoardRepository;
import repository.GameRoomRepository;
import repository.GameStateRepository;

public class GameService {

    private final BoardRepository boardRepository;
    private final GameRoomRepository gameRoomRepository;
    private final GameStateRepository gameStateRepository;
    private final H2ConnectionManager connectionManager;

    public GameService(BoardRepository boardRepository,
                       GameRoomRepository gameRoomRepository,
                       GameStateRepository gameStateRepository,
                       H2ConnectionManager connectionManager) {
        this.boardRepository = boardRepository;
        this.gameRoomRepository = gameRoomRepository;
        this.gameStateRepository = gameStateRepository;
        this.connectionManager = connectionManager;
    }

    public void saveGame(Map<Position, Place> board, String name, Side side) {
        try (Connection conn = H2ConnectionManager.getConnection()) {
            conn.setAutoCommit(false);

            try {
                long roomId = gameRoomRepository.save(name, conn);
                boardRepository.saveBoard(roomId, board, conn);
                gameStateRepository.save(roomId, side, conn);

                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                throw new RuntimeException("게임 저장 실패", e);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<Position, Place> findBoardByRoomId(long roomId) {
        existsById(roomId);
        return boardRepository.findBoard(roomId);
    }

    public GameStateEntity findGameStateByRoomId(long roomId) {
        existsById(roomId);
        return gameStateRepository.findByRoomId(roomId);
    }

    public List<GameRoomEntity> findGameRoomAll() {
        return gameRoomRepository.findAll();
    }

    private void existsById(long roomId) {
        if (!gameRoomRepository.existsById(roomId)) {
            throw new IllegalArgumentException("[ERROR] 없는 방 번호입니다.");
        }
    }
}

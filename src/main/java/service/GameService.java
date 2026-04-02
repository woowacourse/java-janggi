package service;

import config.ConnectionManager;
import java.sql.Connection;
import domain.place.Place;
import domain.place.piece.Side;
import domain.position.Position;
import entity.GameRoomEntity;
import entity.GameStateEntity;
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
    private final ConnectionManager connectionManager;

    public GameService(BoardRepository boardRepository,
                       GameRoomRepository gameRoomRepository,
                       GameStateRepository gameStateRepository,
                       ConnectionManager connectionManager) {
        this.boardRepository = boardRepository;
        this.gameRoomRepository = gameRoomRepository;
        this.gameStateRepository = gameStateRepository;
        this.connectionManager = connectionManager;
    }

    public void saveGame(Map<Position, Place> board, String name, Side side) {
        try (Connection conn = connectionManager.getConnection()) {
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
        try (Connection conn = connectionManager.getConnection()) {
            existsById(roomId, conn);
            return boardRepository.findBoard(roomId, conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public GameStateEntity findGameStateByRoomId(long roomId) {
        try (Connection conn = connectionManager.getConnection()) {
            existsById(roomId, conn);
            return gameStateRepository.findByRoomId(roomId, conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<GameRoomEntity> findGameRoomAll() {
        try (Connection conn = connectionManager.getConnection()) {
            return gameRoomRepository.findAll(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void existsById(long roomId, Connection conn) {
        if (!gameRoomRepository.existsById(roomId, conn)) {
            throw new IllegalArgumentException("[ERROR] 없는 방 번호입니다.");
        }
    }
}

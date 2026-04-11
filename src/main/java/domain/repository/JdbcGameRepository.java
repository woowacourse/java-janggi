package domain.repository;

import domain.Game;
import domain.entity.GameRoomEntity;
import domain.coordinate.Position;
import domain.piece.*;
import persistence.DatabaseConnector;

import java.sql.*;
import java.util.*;

public class JdbcGameRepository implements GameRepository {

    private static final int COL_SIZE = 10;
    private static final int ROW_SIZE = 9;

    private final GameRoomDao gameRoomDao;
    private final BoardStateDao boardStateDao;

    public JdbcGameRepository(GameRoomDao gameRoomDao, BoardStateDao boardStateDao) {
        this.gameRoomDao = gameRoomDao;
        this.boardStateDao = boardStateDao;
    }

    @Override
    public void save(Game game) {
        try (Connection conn = DatabaseConnector.getConnection()) {
            conn.setAutoCommit(false);
            try {
                Long gameId = gameRoomDao.saveOrUpdateGameRoom(conn, game);
                boardStateDao.resetBoard(conn, gameId);
                boardStateDao.saveBoardState(conn, gameId, game.getBoard());

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException("저장 실패: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Game> load(Long gameId) {
        try (Connection conn = DatabaseConnector.getConnection()) {
            Map<Position, Piece> pieceMap = boardStateDao.findPiecesByGameId(conn, gameId);
            if (pieceMap.isEmpty()) return Optional.empty();

            fillEmptyPositions(pieceMap);
            return gameRoomDao.findGameRoomById(conn, gameId, pieceMap);

        } catch (SQLException e) {
            throw new RuntimeException("로드 실패: " + e.getMessage(), e);
        }
    }

    @Override
    public List<GameRoomEntity> findAllRooms() {
        return gameRoomDao.findAllRooms();
    }

    private void fillEmptyPositions(Map<Position, Piece> map) {
        for (int i = 0; i < COL_SIZE; i++) {
            for (int j = 0; j < ROW_SIZE; j++) {
                map.putIfAbsent(Position.of(i, j), EmptyPiece.getInstance());
            }
        }
    }
}

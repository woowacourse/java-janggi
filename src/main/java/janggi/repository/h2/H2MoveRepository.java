package janggi.repository.h2;

import janggi.domain.side.Side;
import janggi.entity.MoveEntity;
import janggi.repository.JdbcDataSource;
import janggi.repository.MoveRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;

public class H2MoveRepository implements MoveRepository {
    private static final String INSERT_MOVE =
            "INSERT INTO MOVE (GAME_ID, MOVE_NUMBER, SIDE, FROM_X, FROM_Y, TO_X, TO_Y) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_MOVE_BY_ID =
            "SELECT ID, GAME_ID, MOVE_NUMBER, SIDE, FROM_X, FROM_Y, TO_X, TO_Y FROM MOVE WHERE ID = ?";
    private static final String SELECT_NEXT_MOVE_NUMBER =
            "SELECT COALESCE(MAX(MOVE_NUMBER), 0) + 1 FROM MOVE WHERE GAME_ID = ?";
    private static final String SELECT_MOVES_BY_GAME_ID =
            "SELECT ID, GAME_ID, MOVE_NUMBER, SIDE, FROM_X, FROM_Y, TO_X, TO_Y FROM MOVE WHERE GAME_ID = ? ORDER BY MOVE_NUMBER";

    private final JdbcDataSource dataSource;

    public H2MoveRepository(JdbcDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(INSERT_MOVE)) {
            stmt.setInt(1, move.gameId());
            stmt.setInt(2, move.moveNumber());
            stmt.setString(3, move.side().name());
            stmt.setInt(4, move.fromX());
            stmt.setInt(5, move.fromY());
            stmt.setInt(6, move.toX());
            stmt.setInt(7, move.toY());
            stmt.executeUpdate();
        } catch (JdbcSQLIntegrityConstraintViolationException e) {
            throw e;
        } catch (SQLException e) {
            throw new IllegalStateException("게임 ID " + move.gameId() + "의 이동 저장에 실패했습니다.", e);
        }
    }

    @Override
    public MoveEntity findById(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SELECT_MOVE_BY_ID)) {
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                return mapResultSetToMove(resultSet);
            }
            return null;
        } catch (SQLException e) {
            throw new IllegalStateException("이동 ID " + id + "의 조회에 실패했습니다.", e);
        }
    }

    @Override
    public List<MoveEntity> findByGameIdOrderByMoveNumber(int gameId) {
        List<MoveEntity> moveEntities = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SELECT_MOVES_BY_GAME_ID)) {
            stmt.setInt(1, gameId);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                moveEntities.add(mapResultSetToMove(resultSet));
            }
            return moveEntities;
        } catch (SQLException e) {
            throw new IllegalStateException("게임 ID " + gameId + "의 이동 조회에 실패했습니다.", e);
        }
    }

    @Override
    public int findNextMoveNumber(int gameId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SELECT_NEXT_MOVE_NUMBER)) {
            stmt.setInt(1, gameId);

            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            return 1;
        } catch (SQLException e) {
            throw new IllegalStateException("게임 ID " + gameId + "의 다음 MOVE_NUMBER 조회에 실패했습니다.");
        }
    }

    private MoveEntity mapResultSetToMove(ResultSet rs) throws SQLException {
        return new MoveEntity(
                rs.getInt("ID"),
                rs.getInt("GAME_ID"),
                rs.getInt("MOVE_NUMBER"),
                Side.valueOf(rs.getString("SIDE")),
                rs.getInt("FROM_X"),
                rs.getInt("FROM_Y"),
                rs.getInt("TO_X"),
                rs.getInt("TO_Y")
        );
    }
}

package janggi.repository.h2;

import static janggi.repository.h2.DataSource.getConnection;

import janggi.domain.side.Side;
import janggi.entity.MoveEntity;
import janggi.repository.MoveRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class H2MoveRepository implements MoveRepository {
    @Override
    public void save(MoveEntity move) {
        String sql = "INSERT INTO MOVE (ID,GAME_ID,SIDE,FROM_X,FROM_Y,TO_X,TO_Y) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, move.id());
            stmt.setInt(2, move.gameId());
            stmt.setString(3, move.side().getName());
            stmt.setInt(4, move.fromX());
            stmt.setInt(5, move.fromY());
            stmt.setInt(6, move.toX());
            stmt.setInt(7, move.toY());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("게임 저장에 실패했습니다.", e);
        }
    }

    @Override
    public MoveEntity findById(int id) {
        String sql = "SELECT ID, GAME_ID, MOVE_NUMBER, SIDE, FROM_X, FROM_Y, TO_X, TO_Y FROM MOVE "
                + "WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                return mapResultSetToMove(resultSet);
            }
            return null;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<MoveEntity> findByGameIdOrderByMoveNumber(int gameId) {
        String sql = "SELECT ID, GAME_ID, MOVE_NUMBER, SIDE, FROM_X, FROM_Y, TO_X, TO_Y FROM MOVE "
                + "WHERE GAME_ID = ? ORDER BY MOVE_NUMBER";

        List<MoveEntity> moveEntities = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, gameId);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                moveEntities.add(mapResultSetToMove(resultSet));
            }
            return moveEntities;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

    }

    private MoveEntity mapResultSetToMove(ResultSet rs) throws SQLException {
        return new MoveEntity(
                rs.getInt("id"),
                rs.getInt("game_id"),
                Side.valueOf(rs.getString("side")),
                rs.getInt("from_x"),
                rs.getInt("from_y"),
                rs.getInt("to_x"),
                rs.getInt("to_y")
        );
    }
}

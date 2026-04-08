package janggi.repository.jdbc;

import janggi.database.DatabaseConnection;
import janggi.model.Team;
import janggi.model.gimul.AbstractGimul;
import janggi.model.gimul.GimulType;
import janggi.model.position.Column;
import janggi.model.position.Position;
import janggi.model.position.Row;
import janggi.repository.GimulRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class JdbcGimulRepository implements GimulRepository {

    @Override
    public void deleteAll(Connection connection, Long gameId) {
        String sql = "DELETE FROM gimul WHERE game_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, gameId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("DB 오류가 발생했습니다.", e);
        }
    }

    @Override
    public void saveAll(Connection connection, Long gameId, Map<Position, AbstractGimul> board) {
        String sql = "INSERT INTO gimul (game_id, gimul_type, team, row_value, column_value) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (Map.Entry<Position, AbstractGimul> entry : board.entrySet()) {
                statement.setLong(1, gameId);
                statement.setString(2, entry.getValue().getType().name());
                statement.setString(3, entry.getValue().getTeam().name());
                statement.setInt(4, entry.getKey().row().getValue());
                statement.setInt(5, entry.getKey().column().getValue());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            throw new IllegalStateException("DB 오류가 발생했습니다.", e);
        }
    }

    public Map<Position, AbstractGimul> findAll(Long gameId) {
        String sql = "SELECT * FROM gimul WHERE game_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, gameId);
            ResultSet resultSet = statement.executeQuery();
            Map<Position, AbstractGimul> board = new HashMap<>();
            while (resultSet.next()) {
                Position position = new Position(
                        Row.of(resultSet.getInt("row_value")),
                        Column.of(resultSet.getInt("column_value"))
                );
                AbstractGimul gimul = GimulType.valueOf(resultSet.getString("gimul_type"))
                        .create(Team.valueOf(resultSet.getString("team")));
                board.put(position, gimul);
            }
            return board;
        } catch (SQLException exception) {
            throw new IllegalStateException("DB 오류가 발생했습니다.", exception);
        }
    }
}

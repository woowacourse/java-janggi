package domain.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.DatabaseConnector;

public class JdbcMoveHistoryDao implements MoveHistoryDao {

    private final DatabaseConnector connector;

    public JdbcMoveHistoryDao(DatabaseConnector connector) {
        this.connector = connector;
    }

    public void addHistory(final String gameId, final String originId, final String destinationId) {
        final String query = "INSERT INTO move_history(game, origin, destination) VALUES(?, ?, ?)";
        try (final var connection = connector.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, Integer.parseInt(gameId));
            preparedStatement.setString(2, originId);
            preparedStatement.setString(3, destinationId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    public void deleteAll() {
        final String query = "DELETE FROM move_history";
        try (final var connection = connector.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("데이터 삽입에 실패했습니다.");
        }
    }

    public List<List<String>> getAllHistory(final String gameId) {
        final String query = "SELECT * FROM move_history WHERE game = ?";
        try (final var connection = connector.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            List<List<String>> positions = new ArrayList<>();
            preparedStatement.setString(1, gameId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String originId = resultSet.getString("origin");
                String destinationId = resultSet.getString("destination");

                positions.add(List.of(originId, destinationId));
            }
            return positions;
        } catch (SQLException e) {
            throw new IllegalStateException("데이터 삽입에 실패했습니다.");
        }
    }
}

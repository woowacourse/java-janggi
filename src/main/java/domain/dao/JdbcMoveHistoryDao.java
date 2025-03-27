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

    public void addHistory(final int gameId, final int originId, final int destinationId) {
        final String query = "INSERT INTO history(game, origin, destination) VALUES(?, ?, ?)";
        try (final var connection = connector.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, gameId);
            preparedStatement.setInt(2, originId);
            preparedStatement.setInt(3, destinationId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    public void deleteAll() {
        final String query = "DELETE FROM history";
        try (final var connection = connector.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("데이터 삽입에 실패했습니다.");
        }
    }

    public List<List<Integer>> getAllHistory(final int gameId) {
        final String query = "SELECT * FROM history WHERE game = ?";
        try (final var connection = connector.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            List<List<Integer>> positions = new ArrayList<>();
            preparedStatement.setInt(1, gameId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int originId = resultSet.getInt("origin");
                int destinationId = resultSet.getInt("destination");

                positions.add(List.of(originId, destinationId));
            }
            return positions;
        } catch (SQLException e) {
            throw new IllegalStateException("데이터 삽입에 실패했습니다.");
        }
    }
}

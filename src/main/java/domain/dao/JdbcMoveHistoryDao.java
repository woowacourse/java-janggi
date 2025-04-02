package domain.dao;

import domain.dto.HistoryDto;
import util.DatabaseConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcMoveHistoryDao implements MoveHistoryDao {

    private final DatabaseConnector connector;

    public JdbcMoveHistoryDao(DatabaseConnector connector) {
        this.connector = connector;
    }

    public void addHistory(final int gameId, final int originId, final int destinationId) {
        final String query = "INSERT INTO move_history(game, origin, destination) VALUES(?, ?, ?)";
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
        final String query = "DELETE FROM move_history";
        try (final var connection = connector.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("데이터 삭제에 실패했습니다.");
        }
    }

    public List<HistoryDto> getAllHistory(final int gameId) {
        final String query = "SELECT * FROM move_history WHERE game = ?";
        try (final var connection = connector.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            List<HistoryDto> positions = new ArrayList<>();
            preparedStatement.setInt(1, gameId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int originId = resultSet.getInt("origin");
                int destinationId = resultSet.getInt("destination");

                positions.add(new HistoryDto(originId, destinationId));
            }
            return positions;
        } catch (SQLException e) {
            throw new IllegalStateException("데이터 탐색에 실패했습니다.");
        }
    }
}

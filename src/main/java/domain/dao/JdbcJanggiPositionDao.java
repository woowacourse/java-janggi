package domain.dao;

import domain.position.JanggiPosition;
import util.DatabaseConnector;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcJanggiPositionDao implements JanggiPositionDao {

    private final DatabaseConnector connector;

    public JdbcJanggiPositionDao(DatabaseConnector connector) {
        this.connector = connector;
    }

    public void addPosition(final JanggiPosition position) {
        final String query = "INSERT INTO janggi_position(rank_value, file_value) VALUES(?, ?)";
        try (final var connection = connector.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, position.getRank());
            preparedStatement.setInt(2, position.getFile());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("데이터 삽입에 실패했습니다.");
        }
    }

    public int findByPosition(JanggiPosition janggiPosition) {
        final String query = "SELECT position_id FROM janggi_position where rank_value = ? and file_value = ?";
        try (final var connection = connector.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, janggiPosition.getRank());
            preparedStatement.setInt(2, janggiPosition.getFile());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("position_id");
            } else {
                addPosition(janggiPosition);
                return findByPosition(janggiPosition);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("데이터 탐색에 실패했습니다.");
        }
    }

    public JanggiPosition findPositionById(int positionId) {
        final String query = "SELECT rank_value, file_value FROM janggi_position where position_id = ?";
        try (final var connection = connector.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, positionId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int rank = resultSet.getInt("rank_value");
                int file = resultSet.getInt("file_value");
                return new JanggiPosition(rank, file);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("데이터 탐색에 실패했습니다.");
        }
        throw new IllegalStateException("위치 탐색에 실패했습니다.");
    }

    public void deleteAll() {
        final String query = "DELETE FROM janggi_position";
        try (final var connection = connector.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("데이터 삭제에 실패했습니다.");
        }
    }
}

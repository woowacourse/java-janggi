package janggi.dao;

import janggi.domain.piece.direction.Position;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class PositionDao {

    private final DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

    public void addPosition(final Position position) {
        final String query = "INSERT INTO position (x,y) VALUES(?, ?)";
        try (final Connection connection = databaseConnection.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, position.x());
            preparedStatement.setInt(2, position.y());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException("오류가 발생했습니다.");
        }
    }

    public Position findPositionById(final int positionId) {
        final String query = "SELECT * FROM position WHERE position_id = ?";
        try (final Connection connection = databaseConnection.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, positionId);
            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    final int x = resultSet.getInt("x");
                    final int y = resultSet.getInt("y");
                    return new Position(x, y);
                }
            }
            throw new RuntimeException("위치를 찾을 수 없습니다.");
        } catch (final SQLException e) {
            throw new RuntimeException("오류가 발생했습니다.");
        }
    }

    public Optional<Integer> findIdByPosition(final Position position) {
        final String query = "SELECT * FROM position WHERE x = ? AND y = ?";
        try (final Connection connection = databaseConnection.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, position.x());
            preparedStatement.setInt(2, position.y());
            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(resultSet.getInt("position_id"));
                }
                return Optional.empty();
            }
        } catch (final SQLException e) {
            throw new RuntimeException("오류가 발생했습니다.");
        }
    }

    public void deletePositionByXY(final int x, final int y) {
        final String query = "DELETE FROM position WHERE x = ? AND y = ?";
        try (final Connection connection = databaseConnection.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, x);
            preparedStatement.setInt(2, y);
            final int rowsAffected = preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException("오류가 발생했습니다.");
        }
    }

    public void deletePosition(final Position position) {
        deletePositionByXY(position.x(), position.y());
    }

    public void deleteAllPositions() {
        final String query = "DELETE FROM position";
        try (final Connection connection = databaseConnection.getConnection();
             final PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate(query);
        } catch (final SQLException e) {
            throw new RuntimeException("오류가 발생했습니다.", e);
        }
    }
}

package janggi.dao;

import janggi.domain.piece.direction.Position;
import java.sql.SQLException;

public class PositionDao {

    private final DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

    public void addPosition(final Position position) {
        final var query = "INSERT INTO position (x,y) VALUES(?, ?)";
        try (final var connection = databaseConnection.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, position.x());
            preparedStatement.setInt(2, position.y());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Position findByPositionId(final int positionId) {
        final var query = "SELECT * FROM position WHERE position_id = ?";
        try (final var connection = databaseConnection.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, positionId);

            final var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Position(
                        resultSet.getInt("x"),
                        resultSet.getInt("y")
                );
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public int findIdByXY(final int x, final int y) {
        final var query = "SELECT position_id FROM position WHERE x = ? AND y = ?";
        try (final var connection = databaseConnection.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, x);
            preparedStatement.setInt(2, y);
            final var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("position_id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }

    public boolean deletePositionById(final int positionId) {
        final var query = "DELETE FROM position WHERE position_id = ?";
        try (final var connection = databaseConnection.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, positionId);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (final SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletePositionByXY(final int x, final int y) {
        final var query = "DELETE FROM position WHERE x = ? AND y = ?";
        try (final var connection = databaseConnection.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, x);
            preparedStatement.setInt(2, y);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (final SQLException e) {
            System.err.println("Position 삭제 오류: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletePosition(final Position position) {
        return deletePositionByXY(position.x(), position.y());
    }

    public void deleteAllPositions() {
        final var query = "DELETE FROM position";
        try (final var connection = databaseConnection.getConnection();
             final var statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (final SQLException e) {
            throw new RuntimeException("Failed to delete all positions", e);
        }
    }
}

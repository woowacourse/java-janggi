package janggi.dao;

import janggi.domain.game.Turn;
import janggi.domain.piece.Side;
import java.sql.Connection;
import java.sql.SQLException;

public class TurnDao {

    public void createTableIfAbsent(final Connection connection) {
        final var query = """
                CREATE TABLE IF NOT EXISTS turn (
                 	id INT AUTO_INCREMENT PRIMARY KEY,
                 	turn VARCHAR(64) NOT NULL
                 );""";
        try (final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.execute();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(final Turn turn, final Connection connection) {
        final var query = "INSERT INTO turn(`turn`) VALUES (?)";
        try (final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, turn.getSide().toString());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(final Turn turn, final Connection connection) {
        final var query = "UPDATE turn SET turn = ?";
        try (final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, turn.getSide().toString());

            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Turn find(final Connection connection) {
        final var query = "SELECT * FROM turn";
        try (final var preparedStatement = connection.prepareStatement(query)) {
            final var resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Turn(Side.valueOf(resultSet.getString("turn")));
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void clear(final Connection connection) {
        final var query = "DELETE FROM turn";
        try (final var preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}


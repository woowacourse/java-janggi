package dao;

import domain.player.Team;
import java.sql.SQLException;
import java.util.Optional;

public class BoardDao {

    private final ConnectionManager connectionManager;

    public BoardDao(final ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public void addBoard(final Team currentTurn) {
        final var query = "INSERT INTO board (current_turn) VALUES(?)";
        try (final var connection = connectionManager.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, currentTurn.name());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Team> findCurrentTurn() {
        final var query = "SELECT * FROM board";
        try (final var connection = connectionManager.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            final var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(Team.getValue(resultSet.getString("current_turn")));
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public void updateCurrentTurn(final Team team) {
        final var query = "UPDATE board SET current_turn = ?";
        try (final var connection = connectionManager.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, team.name());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void clear() {
        final var query = "DELETE FROM board";
        try (final var connection = connectionManager.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

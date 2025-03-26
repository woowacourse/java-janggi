package save;

import java.sql.SQLException;
import java.util.Optional;
import piece.player.Team;

public class JanggiTurnDao {

    private final MySQLConnection connection;

    public JanggiTurnDao(MySQLConnection mySQLConnection) {
        this.connection = mySQLConnection;
    }

    public void addTurnScore(Team team, int turn, int score) {
        final var query = "INSERT INTO janggi_turn (team, turn, score) VALUES(?, ?, ?)";
        try (final var connection = this.connection.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, team.name());
            preparedStatement.setInt(2, turn);
            preparedStatement.setInt(3, score);
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int findIdByTeamTurn(Team team, int turn) {
        final var query = "SELECT * FROM janggi_turn WHERE team = ? and turn = ?";
        try (final var connection = this.connection.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, team.name());
            preparedStatement.setInt(2, turn);

            final var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }

        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public Optional<Integer> getLatestTurnId() {
        final var query = "SELECT id FROM janggi_turn ORDER BY turn DESC LIMIT 1";

        try (final var connection = this.connection.getConnection()) {
            final var preparedStatement = connection.prepareStatement(query);

            final var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(resultSet.getInt("id"));
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public Optional<Integer> getLatestTurn() {
        final var query = "SELECT turn FROM janggi_turn ORDER BY turn DESC LIMIT 1";

        try (final var connection = this.connection.getConnection()) {
            final var preparedStatement = connection.prepareStatement(query);

            final var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(resultSet.getInt("turn"));
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();

    }

    public void deleteAll() {
        final var query = "DELETE FROM janggi_turn";
        try (final var connection = this.connection.getConnection()) {
            final var preparedStatement = connection.prepareStatement(query);
            final var resultSet = preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deletePreviousTurnScore(Team team, int turn) {
        final var query = "DELETE FROM janggi_turn WHERE team = ? AND turn = ?";
        try (final var connection = this.connection.getConnection()) {
            final var preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, team.name());
            preparedStatement.setInt(2, turn);
            final var resultSet = preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

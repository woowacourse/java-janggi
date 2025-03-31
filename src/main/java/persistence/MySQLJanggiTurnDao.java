package persistence;

import java.sql.SQLException;
import java.util.Optional;
import piece.player.Team;

public class MySQLJanggiTurnDao implements JanggiTurnDao {

    private final DatabaseConnection connection;

    public MySQLJanggiTurnDao(DatabaseConnection mySQLConnection) {
        if (!(mySQLConnection instanceof MySQLConnection)) {
            throw new InvalidConnection("MySQL 커넥션만 지원합니다.");
        }
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
            throw new PersistenceFailException(e);
        }
    }

    public Optional<Integer> findLatestTurnId() {
        final var query = "SELECT id FROM janggi_turn ORDER BY turn DESC LIMIT 1";

        try (final var connection = this.connection.getConnection()) {
            final var preparedStatement = connection.prepareStatement(query);

            final var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(resultSet.getInt("id"));
            }
        } catch (final SQLException e) {
            throw new PersistenceFailException(e);
        }
        return Optional.empty();
    }

    public Optional<Integer> findLatestTurn() {
        final var query = "SELECT turn FROM janggi_turn ORDER BY turn DESC LIMIT 1";

        try (final var connection = this.connection.getConnection()) {
            final var preparedStatement = connection.prepareStatement(query);

            final var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(resultSet.getInt("turn"));
            }
        } catch (final SQLException e) {
            throw new PersistenceFailException(e);
        }
        return Optional.empty();

    }

    public void deleteAll() {
        final var query = "DELETE FROM janggi_turn";
        try (final var connection = this.connection.getConnection()) {
            final var preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new PersistenceFailException(e);
        }
    }
}

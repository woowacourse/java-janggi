package janggi.db;

import janggi.domain.Team;
import java.sql.SQLException;

public class TurnDao {
    private final Connection connection;

    public TurnDao(Connection connection) {
        this.connection = connection;
    }

    public void addTeam(final Team team) {
        final var query = "INSERT INTO turn VALUES(NULL, ?)";
        try (final var conn = connection.getConnection();
             final var preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, team.name());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Team readTeam() {
        final var query = "SELECT * FROM turn";
        try (final var conn = connection.getConnection();
             final var preparedStatement = conn.prepareStatement(query)) {
            final var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Team.valueOf(resultSet.getString("team"));
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void updateTeam(final Team currentTeam) {
        final var query = "UPDATE turn SET team = ? WHERE id = 1";
        try (final var conn = connection.getConnection();
             final var preparedStatement = conn.prepareStatement(query)) {
            Team nextTurn = Team.getOtherTeam(currentTeam);
            preparedStatement.setString(1, nextTurn.name());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

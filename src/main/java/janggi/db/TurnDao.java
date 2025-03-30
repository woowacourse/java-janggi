package janggi.db;

import janggi.domain.Team;
import java.sql.SQLException;

public class TurnDao {
    public void addTeam(final Team team) {
        final var query = "INSERT INTO turn VALUES(NULL, ?)";
        try (final var connection = new Connection().getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, team.name());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Team readTeam() {
        final var query = "SELECT * FROM turn";
        try (final var connection = new Connection().getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {

            final var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Team.valueOf(resultSet.getString("team"));
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void updateTeam(Team currentTeam) {
        final var query = "UPDATE turn SET team = ? WHERE id = 1";
        try (final var connection = new Connection().getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            Team nextTurn = Team.getOtherTeam(currentTeam);
            preparedStatement.setString(1, nextTurn.name());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteTeamTable() {
        final var query = "TRUNCATE TABLE turn;";
        try (final var connection = new Connection().getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

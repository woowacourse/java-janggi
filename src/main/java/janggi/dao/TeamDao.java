package janggi.dao;

import janggi.domain.Team;
import java.sql.SQLException;

public class TeamDao {

    private final DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

    public Team findTeamById(int teamId) {

        final var query = "SELECT * FROM team WHERE team_id = ?";
        try (final var connection = databaseConnection.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, teamId);

            final var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Team.valueOf(resultSet.getString("team_name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return null;
    }

    public int findTeamIdByName(final Team currentTeam) {
        final var query = "SELECT * FROM team WHERE team_name = ?";
        try (final var connection = databaseConnection.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, currentTeam.name());

            final var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("team_id");
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return -1;
    }
}

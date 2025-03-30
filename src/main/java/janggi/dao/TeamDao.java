package janggi.dao;

import janggi.domain.Team;
import java.sql.SQLException;

public class TeamDao {

    private final DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

    public Team findTeamById(final int teamId) {

        final var query = "SELECT * FROM team WHERE team_id = ?";
        try (final var connection = databaseConnection.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, teamId);

            final var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Team.valueOf(resultSet.getString("team_name"));
            }
            throw new IllegalArgumentException("존재하지 않는 팀입니다.");
        } catch (final SQLException e) {
            throw new RuntimeException();
        }
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
            throw new IllegalArgumentException("존재하지 않는 팀입니다.");
        } catch (final SQLException e) {
            throw new RuntimeException();
        }
    }
}

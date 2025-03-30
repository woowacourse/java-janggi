package janggi.dao;

import janggi.domain.Team;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TeamDao {

    private final DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

    public Team findTeamById(final int teamId) {

        final String query = "SELECT * FROM team WHERE team_id = ?";
        try (final Connection connection = databaseConnection.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, teamId);

            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Team.valueOf(resultSet.getString("team_name"));
            }
            throw new IllegalArgumentException("존재하지 않는 팀입니다.");
        } catch (final SQLException e) {
            throw new RuntimeException();
        }
    }

    public int findIdByTeam(final Team currentTeam) {
        final String query = "SELECT * FROM team WHERE team_name = ?";
        try (final Connection connection = databaseConnection.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, currentTeam.name());

            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("team_id");
            }
            throw new IllegalArgumentException("존재하지 않는 팀입니다.");
        } catch (final SQLException e) {
            throw new RuntimeException();
        }
    }
}

package janggi.dao;

import janggi.domain.Team;
import janggi.domain.Turn;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TurnDao {

    private final DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
    private final TeamDao teamDao = new TeamDao();

    public void addTurn(Turn turn) {
        final String query = "INSERT INTO turn (team_id) VALUES (?)";
        try (final Connection connection = databaseConnection.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            Team currentTeam = turn.getCurrentTurn();
            int teamId = teamDao.findTeamIdByName(currentTeam);

            preparedStatement.setInt(1, teamId);
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException("Failed to add turn", e);
        }
    }

    public Turn findTurn() {
        final String query = "SELECT * FROM turn";
        try (final Connection connection = databaseConnection.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int teamId = resultSet.getInt("team_id");
                    Team team = teamDao.findTeamById(teamId);
                    return new Turn(team);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("턴 정보를 찾을 수 없습니다.");
        }
        return null;
    }

    public void updateTurn(Turn turn) {
        final String query = "UPDATE turn SET team_id = ?";
        try (final Connection connection = databaseConnection.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            Team currentTeam = turn.getCurrentTurn();
            int teamId = teamDao.findTeamIdByName(currentTeam);

            preparedStatement.setInt(1, teamId);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 0) {
                addTurn(turn);
            }
        } catch (final SQLException e) {
            throw new RuntimeException("Failed to update turn", e);
        }
    }

    public void deleteTurn() {
        final String query = "DELETE FROM turn";
        try (final Connection connection = databaseConnection.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate(query);
        } catch (final SQLException e) {
            throw new RuntimeException("Failed to delete turn", e);
        }
    }
}

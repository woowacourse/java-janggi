package dao;

import domain.type.JanggiTeam;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class JanggiTurnDao implements TurnDao {
    private final DatabaseConnector databaseConnector;

    public JanggiTurnDao(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }

    @Override
    public void deleteAll() {
        final var query = "TRUNCATE turn";
        try (final var connection = databaseConnector.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(JanggiTeam team) {
        deleteAll();
        final String query = "INSERT INTO turn(team_id) VALUES(?)";
        try (final var connection = databaseConnector.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, findTeamId(team));
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int findTeamId(JanggiTeam team) {
        final String query = "SELECT id FROM team WHERE name = ?";
        try (final var connection = databaseConnector.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, team.name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
            throw new RuntimeException(team + " 팀이 존재하지 않습니다.");
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JanggiTeam findTurn() {
        final var query = "SELECT team.name as name FROM turn JOIN team ON turn.team_id = team.id";
        try (final var connection = databaseConnector.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            final var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                final String name = resultSet.getString("name");
                return Arrays.stream(JanggiTeam.values())
                        .filter(team -> team.name.equals(name))
                        .findFirst()
                        .orElse(null);
            }
            return null;
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

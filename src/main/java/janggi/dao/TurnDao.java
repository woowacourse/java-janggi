package janggi.dao;

import janggi.domain.Team;
import janggi.domain.Turn;
import java.sql.Connection;
import java.sql.SQLException;

public class TurnDao {

    private final Connection connection;

    public TurnDao(Connection connection) {
        this.connection = connection;
    }

    public void saveTurn(Turn turn) throws SQLException {
        final var query = "INSERT INTO turn(team) VALUES(?)";
        try (final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, turn.getTeam().name());
            preparedStatement.executeUpdate();
        }
    }

    public Turn loadTurn() throws SQLException {
        final var query = "SELECT team FROM turn";
        try (final var preparedStatement = connection.prepareStatement(query)) {
            final var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Team team = Team.valueOf(resultSet.getString("team"));
                return new Turn(team);
            }
            throw new IllegalArgumentException("턴이 존재하지 않습니다");
        }
    }

    public void deleteTurn() throws SQLException {
        final var query = "DELETE FROM turn";
        try (final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        }
    }
}

package dao;

import domain.Team;
import domain.Turn;
import java.sql.Connection;
import java.sql.SQLException;

public class JanggiDao {

    public Turn findAnyTurn(final Connection connection) {
        final var query = "SELECT * FROM janggi";
        try (final var preparedStatement = connection.prepareStatement(query)) {

            final var resultSet = preparedStatement.executeQuery();

            final Team team = Team.from(resultSet.getString("turn"));
            return new Turn(team);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateAnyTeam(
            final Connection connection,
            final Team changedTeam
    ) {
        final var query = "UPDATE janggi SET turn = ? WHERE TRUE";
        try (final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, changedTeam.getTitle());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

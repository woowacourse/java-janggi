package dao;

import domain.player.Player;
import domain.player.Team;
import java.sql.SQLException;
import java.util.Optional;

public class PlayerDao {

    public void addPlayer(final Player player) {
        final var query = "INSERT INTO player (name, team) VALUES(?, ?)";
        try (final var connection = DatabaseConnectionManager.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, player.name());
            preparedStatement.setString(2, player.team().name());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Player> findPlayerByTeam(final Team team) {
        final var query = "SELECT * FROM player WHERE team = ?";
        try (final var connection = DatabaseConnectionManager.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, team.name());

            final var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new Player(
                        resultSet.getString("name"),
                        Team.getValue(resultSet.getString("team")))
                );
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    public void clear() {
        final var query = "DELETE FROM player";
        try (final var connection = DatabaseConnectionManager.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

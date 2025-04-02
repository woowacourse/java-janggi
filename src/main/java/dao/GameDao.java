package dao;

import domain.game.Turn;
import domain.piece.Team;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class GameDao {
    private final JdbcConnection jdbcConnection;

    public GameDao(JdbcConnection jdbcConnection) {
        this.jdbcConnection = jdbcConnection;
    }

    public void insertGameTurn(Team team) {
        final var query = "INSERT INTO game (turn) VALUES (?)";

        try (Connection connection = jdbcConnection.getConnection();
             PreparedStatement insertStmt = connection.prepareStatement(query)) {
            insertStmt.setString(1, team.name());
            insertStmt.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Turn> findTurnByGameId(int gameId) {
        createGameTableIfNotExists();
        final var query = "SELECT turn FROM game WHERE game_id = ?";

        try (Connection connection = jdbcConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, gameId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return Optional.empty();
                }
                String turnName = rs.getString("turn");
                Turn turn = new Turn(Team.getTeamByName(turnName));
                return Optional.of(turn);
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveTurn(Turn turn) {
        final var query = "UPDATE game SET turn = ?";
        try (Connection connection = jdbcConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, turn.getTeam().name());
            stmt.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void createGameTableIfNotExists() {
        final var query = """
                CREATE TABLE IF NOT EXISTS game (
                game_id INT AUTO_INCREMENT PRIMARY KEY,
                turn VARCHAR(64) NOT NULL)
                """;

        try (Connection connection = jdbcConnection.getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.execute(query);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

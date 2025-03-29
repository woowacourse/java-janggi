package dao;

import domain.game.Turn;
import domain.piece.Team;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GameDao {
    private final Connection connection = JdbcConnection.getInstance();

    public void createGameTableIfNotExists() {
        final var query = "CREATE TABLE IF NOT EXISTS game ("
                + "game_id INT AUTO_INCREMENT PRIMARY KEY, "
                + "turn VARCHAR(64) NOT NULL)";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(query);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isGameContinuing() {
        final var query = "SELECT COUNT(*) FROM game";
        try (PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            return rs.next() && rs.getInt(1) > 0;
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertGameTurn(Team team) {
        final var query = "INSERT INTO game (turn) VALUES (?)";

        try (PreparedStatement insertStmt = connection.prepareStatement(query)) {
            insertStmt.setString(1, team.name());
            insertStmt.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void saveTurn(Turn turn) {
        final var query = "UPDATE game SET turn = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, turn.getTeam().name());
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

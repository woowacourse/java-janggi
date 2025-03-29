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
        final var createTableQuery = "CREATE TABLE IF NOT EXISTS game ("
                + "game_id INT AUTO_INCREMENT PRIMARY KEY, "
                + "turn VARCHAR(64) NOT NULL)";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableQuery);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void initializeGameIfNotExists() {
        final var insertInitialTurnQuery = "INSERT INTO game (turn) VALUES (?)";

        String checkQuery = "SELECT COUNT(*) FROM game";
        try (PreparedStatement pstmt = connection.prepareStatement(checkQuery);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next() && rs.getInt(1) == 0) {
                try (PreparedStatement insertStmt = connection.prepareStatement(insertInitialTurnQuery)) {
                    insertStmt.setString(1, Team.CHO.name());  // 초기 턴을 "CHO"로 설정
                    insertStmt.executeUpdate();
                }
            }
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

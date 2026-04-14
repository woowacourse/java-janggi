package dao;

import dto.GameStateData;
import infrastructure.TransactionContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class JanggiGameStateDao {

    public void insert(long gameId, String status, boolean isFinished) throws SQLException {
        String sql = "INSERT INTO game_state (game_id, status, is_finished, last_played_at) VALUES (?, ?, ?, CURRENT_TIMESTAMP)";

        try (PreparedStatement ps = TransactionContext.getPreparedStatement(sql)) {
            ps.setLong(1, gameId);
            ps.setString(2, status);
            ps.setBoolean(3, isFinished);
            ps.executeUpdate();
        }
    }

    public void update(long gameId, String status, boolean isFinished) throws SQLException {
        String sql = "UPDATE game_state SET status = ?, is_finished = ?, last_played_at = CURRENT_TIMESTAMP WHERE game_id = ?";

        try (PreparedStatement ps = TransactionContext.getPreparedStatement(sql)) {
            ps.setString(1, status);
            ps.setBoolean(2, isFinished);
            ps.setLong(3, gameId);
            ps.executeUpdate();
        }
    }

    public Optional<GameStateData> findByGameId(long gameId) throws SQLException {
        String sql = "SELECT status, is_finished FROM game_state WHERE game_id = ?";

        try (PreparedStatement ps = TransactionContext.getPreparedStatement(sql)) {
            ps.setLong(1, gameId);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return Optional.empty();
                }
                return Optional.of(new GameStateData(rs.getString("status"), rs.getBoolean("is_finished")));
            }
        }
    }
}

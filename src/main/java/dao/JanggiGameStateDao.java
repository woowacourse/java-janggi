package dao;

import dto.GameStateData;
import infrastructure.TransactionContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    public GameStateData findByGameId(long gameId) throws SQLException {
        String sql = "SELECT status, is_finished FROM game_state WHERE game_id = ?";

        try (PreparedStatement ps = TransactionContext.getPreparedStatement(sql)) {
            ps.setLong(1, gameId);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new SQLException("game_state 없음: game_id=" + gameId);
                }
                return new GameStateData(rs.getString("status"), rs.getBoolean("is_finished"));
            }
        }
    }
//
//    public Optional<Long> findLatestUnfinishedGameId() throws SQLException {
//        String sql = "SELECT gameId FROM game_state WHERE status IN (?, ?) ORDER BY gameId DESC LIMIT 1";
//        try (PreparedStatement ps = TransactionContext.getPreparedStatement(sql)) {
//            ps.setString(1, GameStatus.GREEN_PLAYER_TURN.name());
//            ps.setString(2, GameStatus.RED_PLAYER_TURN.name());
//            try (ResultSet rs = ps.executeQuery()) {
//                if (rs.next()) return Optional.of(rs.getLong("gameId"));
//                return Optional.empty();
//            }
//        }
//    }

//    public Optional<Long> findLatestUnfinishedGameId() throws SQLException {
//        String sql = "SELECT gameId FROM game_state WHERE is_finished = false ORDER BY gameId DESC LIMIT 1";
//
//        try (PreparedStatement ps =  TransactionContext.getPreparedStatement(sql);
//             ResultSet rs = ps.executeQuery()) {
//            if (rs.next()) {
//                return Optional.of(rs.getLong("gameId"));
//            }
//            return Optional.empty();
//        }
//    }
}

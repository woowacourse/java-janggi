package janggi.repository;

import janggi.domain.side.Side;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class GameDao {
    private final JdbcContext jdbcContext;

    public GameDao(JdbcContext jdbcContext) {
        this.jdbcContext = jdbcContext;
    }

    public int insert(Connection conn, Side turn) throws SQLException {
        String sql = "INSERT INTO game (turn) VALUES (?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, turn.name());
            pstmt.executeUpdate();
            ResultSet keys = pstmt.getGeneratedKeys();
            if (keys.next()) {
                return keys.getInt(1);
            }
            throw new SQLException("게임 저장에 실패했습니다.");
        }
    }

    public void updateTurn(Connection conn, int gameId, Side turn) throws SQLException {
        String sql = "UPDATE game SET turn = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, turn.name());
            pstmt.setInt(2, gameId);
            pstmt.executeUpdate();
        }
    }

    public void finish(Connection conn, int gameId, Side winner) throws SQLException {
        String sql = "UPDATE game SET is_finished = 1, winner = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, winner.name());
            pstmt.setInt(2, gameId);
            pstmt.executeUpdate();
        }
    }

    public Optional<Integer> findActiveGameId() {
        String sql = "SELECT id FROM game WHERE is_finished = 0 ORDER BY id DESC LIMIT 1";
        try (Connection conn = jdbcContext.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return Optional.of(rs.getInt("id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("게임 조회에 실패하였습니다.", e);
        }
        return Optional.empty();
    }

    public Side findTurn(int gameId) {
        String sql = "SELECT turn FROM game WHERE id = ?";
        try (Connection conn = jdbcContext.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, gameId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Side.valueOf(rs.getString("turn"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("턴 정보를 불러오지 못했습니다.", e);
        }
        throw new RuntimeException("턴 정보를 불러오지 못했습니다.");
    }
}

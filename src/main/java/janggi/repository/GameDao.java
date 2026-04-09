package janggi.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import janggi.domain.side.Side;

public class GameDao {

    public int insert(Side turn) {
        Connection conn = TransactionManager.getConnection();
        String sql = "INSERT INTO game (turn) VALUES (?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, turn.name());
            pstmt.executeUpdate();
            ResultSet keys = pstmt.getGeneratedKeys();
            if (keys.next()) {
                return keys.getInt(1);
            }
            throw new RuntimeException("게임 저장에 실패했습니다.");
        } catch (SQLException e) {
            throw new RuntimeException("게임 저장에 실패했습니다.", e);
        }
    }

    public void updateTurn(int gameId, Side turn) {
        Connection conn = TransactionManager.getConnection();
        String sql = "UPDATE game SET turn = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, turn.name());
            pstmt.setInt(2, gameId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("턴 업데이트에 실패했습니다.", e);
        }
    }

    public void finish(int gameId, Side winner) {
        Connection conn = TransactionManager.getConnection();
        String sql = "UPDATE game SET is_finished = 1, winner = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, winner.name());
            pstmt.setInt(2, gameId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("게임 종료 처리에 실패했습니다.", e);
        }
    }

    public Optional<Integer> findActiveGameId() {
        Connection conn = TransactionManager.getConnection();
        String sql = "SELECT id FROM game WHERE is_finished = 0 ORDER BY id DESC LIMIT 1";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
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
        Connection conn = TransactionManager.getConnection();
        String sql = "SELECT turn FROM game WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
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

package repository;


import domain.piece.Team;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GameDao {
    // 기존 save 메서드는 그대로 둡니다.
    public long save(String name, String turn) {
        String sql = "INSERT INTO game (turn) VALUES (?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, turn);
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) return rs.getLong(1);
            }
            throw new SQLException("ID 생성 실패");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateTurn(long gameId, String turn) {
        String sql = "UPDATE game SET turn = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, turn);
            pstmt.setLong(2, gameId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("턴 업데이트 중 에러 발생", e);
        }
    }

    // 🌟 추가: 이 게임 방이 누구 차례였는지 DB에서 꺼내옵니다.
    public Team findTurn(long gameId) {
        String sql = "SELECT turn FROM game WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, gameId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Team.valueOf(rs.getString("turn"));
                }
            }
            throw new IllegalArgumentException("존재하지 않는 게임 방입니다.");
        } catch (SQLException e) {
            throw new RuntimeException("차례 불러오기 실패", e);
        }
    }
}

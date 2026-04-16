package db;

import domain.Team;
import java.sql.*;

public class BoardDao {

    public long create() {
        String sql = "INSERT INTO board (status, turn) VALUES ('playing', ?)";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, Team.CHO.name());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
            throw new RuntimeException("board_id 획득 실패");

        } catch (SQLException e) {
            throw new RuntimeException("게임 생성 실패", e);
        }
    }

    public long findLatestPlaying() {
        String sql = "SELECT id FROM board WHERE status = 'playing' ORDER BY id DESC LIMIT 1";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                return rs.getLong("id");
            }
            return -1;

        } catch (SQLException e) {
            throw new RuntimeException("진행 중인 게임 조회 실패", e);
        }
    }

    public void updateTurn(long boardId, String turn) {
        String sql = "UPDATE board SET turn = ? WHERE id = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, turn);
            stmt.setLong(2, boardId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("턴 업데이트 실패", e);
        }
    }

    public String findTurn(long boardId) {
        String sql = "SELECT turn FROM board WHERE id = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, boardId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("turn");
                }
            }
            throw new RuntimeException("turn 조회 실패");

        } catch (SQLException e) {
            throw new RuntimeException("turn 조회 실패", e);
        }
    }

    public void finish(long boardId) {
        String sql = "UPDATE board SET status = 'finished' WHERE id = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, boardId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("게임 종료 실패", e);
        }
    }
}

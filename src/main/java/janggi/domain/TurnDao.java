package janggi.domain;

import janggi.dto.TurnDto;

import java.sql.*;

public class TurnDao {

    public long save(TurnDto turnDto) {
        String sql = "INSERT INTO turn (game_id, current_turn_team) VALUES (?, ?)";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setLong(1, turnDto.gameId());
            pstmt.setString(2, turnDto.currentTurnTeam());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getLong(1);
            }
            throw new SQLException("턴 데이터 저장 중 오류가 발생했습니다.");
        } catch (SQLException e) {
            throw new RuntimeException("턴 데이터 저장 중 오류가 발생했습니다.", e);
        }
    }
}

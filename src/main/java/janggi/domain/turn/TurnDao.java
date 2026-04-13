package janggi.domain.turn;

import janggi.domain.DatabaseConnector;
import janggi.dto.TurnDto;

import java.sql.*;
import java.util.Optional;

public class TurnDao {

    public void save(TurnDto turnDto) {
        String sql = "INSERT INTO turn (id, game_id, current_turn_team, turn_status) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, turnDto.id());
            pstmt.setLong(2, turnDto.gameId());
            pstmt.setString(3, turnDto.currentTurnTeamName());
            pstmt.setString(4, turnDto.turnStatusFormat());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("턴 데이터 저장 중 오류가 발생했습니다.", e);
        }
    }

    public Optional<TurnDto> findLastTurnByGameId(long gameId) {
        String sql = "SELECT id, game_id, current_turn_team, turn_status FROM turn " +
                "WHERE game_id = ? ORDER BY id DESC LIMIT 1";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, gameId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(TurnDto.of(
                            rs.getLong("id"),
                            rs.getLong("game_id"),
                            rs.getString("current_turn_team"),
                            rs.getString("turn_status")
                    ));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("최신 턴 정보 조회 중 오류 발생", e);
        }
    }
}

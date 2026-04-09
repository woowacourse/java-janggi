package janggi.domain;

import janggi.dto.PieceLogDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class PieceLogDao {

    public void saveAll(List<PieceLogDto> pieceLogDtos) {
        String sql = "INSERT INTO piece_log (turn_id, piece_type, team_type, x, y) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            conn.setAutoCommit(false);

            for (PieceLogDto dto : pieceLogDtos) {
                pstmt.setLong(1, dto.turnId());
                pstmt.setString(2, dto.pieceType());
                pstmt.setString(3, dto.teamType());
                pstmt.setInt(4, dto.x());
                pstmt.setInt(5, dto.y());
                pstmt.addBatch();
            }

            pstmt.executeBatch();
            conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException("생존 기물 데이터 저장 중 오류가 발생했습니다.", e);
        }
    }
}

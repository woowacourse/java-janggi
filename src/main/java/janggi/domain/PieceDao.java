package janggi.domain;

import janggi.dto.PieceDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PieceDao {

    public void saveAll(List<PieceDto> pieceDtos) {
        String sql = "INSERT INTO piece (turn_id, piece_type, team_type, x, y) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            conn.setAutoCommit(false);

            for (PieceDto pieceDto : pieceDtos) {
                pstmt.setLong(1, pieceDto.turnId());
                pstmt.setString(2, pieceDto.pieceType().getName());
                pstmt.setString(3, pieceDto.teamType().getName());
                pstmt.setInt(4, pieceDto.x());
                pstmt.setInt(5, pieceDto.y());
                pstmt.addBatch();
            }

            pstmt.executeBatch();
            conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException("생존 기물 데이터 저장 중 오류가 발생했습니다.", e);
        }
    }

    public List<PieceDto> findPiecesByTurnId(Long turnId) {
        String sql = "SELECT id, turn_id, piece_type, team_type, x, y FROM piece WHERE turn_id = ?";
        List<PieceDto> pieceDtos = new ArrayList<>();

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, turnId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    pieceDtos.add(PieceDto.of(
                            rs.getLong("id"),
                            rs.getLong("turn_id"),
                            rs.getString("piece_type"),
                            rs.getString("team_type"),
                            rs.getInt("x"),
                            rs.getInt("y")
                    ));
                }
            }
            return pieceDtos;
        } catch (SQLException e) {
            throw new RuntimeException("생존 기물 배치 데이터를 불러오는 중 오류가 발생했습니다.", e);
        }
    }
}

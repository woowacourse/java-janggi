package db;

import domain.dto.JanggiBoardDto;
import domain.dto.PieceDto;
import domain.piece.PieceType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PieceDao {
    // 기물 전체 저장 (게임 시작 시)
    public void saveAll(long boardId, JanggiBoardDto boardDto) {
        String sql = "INSERT INTO piece (board_id, `row`, `col`, piece_type, team) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (PieceDto pieceDto : boardDto.getPieces()) {
                if (pieceDto.pieceType() == PieceType.BLANK) continue; // Blank는 저장 안 함

                stmt.setLong(1, boardId);
                stmt.setInt(2, pieceDto.row());
                stmt.setInt(3, pieceDto.col());
                stmt.setString(4, pieceDto.pieceType().name());
                stmt.setString(5, pieceDto.team().name());
                stmt.addBatch();
            }
            stmt.executeBatch();

        } catch (SQLException e) {
            throw new RuntimeException("기물 전체 저장 실패", e);
        }
    }

    // 기물 이동 + 잡힌 기물 삭제 → 트랜잭션으로 묶음
    public void move(long boardId, int fromRow, int fromCol, int toRow, int toCol) {
        String deleteSql = "DELETE FROM piece WHERE board_id = ? AND `row` = ? AND `col` = ?";
        String moveSql = "UPDATE piece SET `row` = ?, `col` = ? WHERE board_id = ? AND `row` = ? AND `col` = ?";

        Connection conn = null;
        try {
            conn = DbConnection.getConnection();
            conn.setAutoCommit(false); // 트랜잭션 시작

            // 1. 도착지 기물 삭제
            try (PreparedStatement stmt = conn.prepareStatement(deleteSql)) {
                stmt.setLong(1, boardId);
                stmt.setInt(2, toRow);
                stmt.setInt(3, toCol);
                stmt.executeUpdate();
            }

            // 2. 기물 이동
            try (PreparedStatement stmt = conn.prepareStatement(moveSql)) {
                stmt.setInt(1, toRow);
                stmt.setInt(2, toCol);
                stmt.setLong(3, boardId);
                stmt.setInt(4, fromRow);
                stmt.setInt(5, fromCol);
                stmt.executeUpdate();
            }

            conn.commit(); // 둘 다 성공 시 커밋

        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback(); // 실패 시 롤백
            } catch (SQLException ex) {
                throw new RuntimeException("롤백 실패", ex);
            }
            throw new RuntimeException("기물 이동 실패", e);
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // 특정 게임의 모든 기물 조회
    public List<String> findAll(long boardId) {
        String sql = "SELECT `row`, `col`, piece_type, team FROM piece WHERE board_id = ?";
        List<String> pieces = new ArrayList<>();

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, boardId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                pieces.add(String.format("(%d,%d) %s %s",
                        rs.getInt("row"),
                        rs.getInt("col"),
                        rs.getString("piece_type"),
                        rs.getString("team")));
            }

        } catch (SQLException e) {
            throw new RuntimeException("기물 조회 실패", e);
        }

        return pieces;
    }
}

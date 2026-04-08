package janggi.db.dao;

import janggi.db.entity.PieceEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcPieceDao {

    public void insertAll(Connection conn, Long gameId, List<PieceEntity> pieces) {
        String sql = "INSERT INTO piece (game_id, piece_type, team, position_x, position_y) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (PieceEntity piece : pieces) {
                pstmt.setLong(1, gameId);
                pstmt.setString(2, piece.getPieceType());
                pstmt.setString(3, piece.getTeam());
                pstmt.setInt(4, piece.getPositionX());
                pstmt.setInt(5, piece.getPositionY());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException("기물 저장을 실패했습니다.");
        }
    }

    public List<PieceEntity> selectByGameId(Connection conn, Long gameId) {
        String sql = "SELECT id, game_id, piece_type, team, position_x, position_y " +
                "FROM piece " +
                "WHERE game_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, gameId);
            ResultSet rs = pstmt.executeQuery();

            List<PieceEntity> pieces = new ArrayList<>();
            while (rs.next()) {
                pieces.add(new PieceEntity(
                        rs.getLong("id"),
                        rs.getLong("game_id"),
                        rs.getString("piece_type"),
                        rs.getString("team"),
                        rs.getInt("position_x"),
                        rs.getInt("position_y")
                ));
            }
            return pieces;
        } catch (SQLException e) {
            throw new RuntimeException("기물 조회를 실패했습니다.");
        }
    }

    public void deleteByGameId(Connection conn, Long gameId, int x, int y) {
        String sql = "DELETE FROM piece " +
                "WHERE game_id = ? " +
                "AND position_x = ? " +
                "AND position_y = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, gameId);
            pstmt.setInt(2, x);
            pstmt.setInt(3, y);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("기물 삭제에 실패했습니다.", e);
        }
    }

    public void update(Connection conn, Long gameId, int fromX, int fromY, int toX, int toY) {
        String sql = "UPDATE piece " +
                "SET position_x = ?, position_y = ? " +
                "WHERE game_id = ? " +
                "AND position_x = ? " +
                "AND position_y = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, toX);
            pstmt.setInt(2, toY);
            pstmt.setLong(3, gameId);
            pstmt.setInt(4, fromX);
            pstmt.setInt(5, fromY);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("기물 위치 업데이트에 실패했습니다.", e);
        }
    }
}

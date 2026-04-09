package janggi.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import janggi.domain.board.coordinate.Point;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.unit.Piece;
import janggi.domain.side.Side;

public class PieceDao {

    public void insertAll(Connection conn, int gameId, Map<Point, Piece> board) {
        String sql = "INSERT INTO game_piece (game_id, piece_type, side, x, y) VALUES (?,?,?,?,?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (Map.Entry<Point, Piece> entry : board.entrySet()) {
                Point point = entry.getKey();
                Piece piece = entry.getValue();
                pstmt.setInt(1, gameId);
                pstmt.setString(2, piece.getType().name());
                pstmt.setString(3, piece.getSide().name());
                pstmt.setInt(4, point.x());
                pstmt.setInt(5, point.y());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException("기물 저장에 실패했습니다.", e);
        }
    }

    public void deleteAll(Connection conn, int gameId) {
        String sql = "DELETE FROM game_piece WHERE game_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, gameId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("기물 삭제에 실패했습니다.", e);
        }
    }

    public Map<Point, Piece> findAll(Connection conn, int gameId) {
        String sql = "SELECT piece_type, side, x, y FROM game_piece WHERE game_id = ?";
        Map<Point, Piece> board = new HashMap<>();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, gameId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    PieceType pieceType = PieceType.valueOf(rs.getString("piece_type"));
                    Side side = Side.valueOf(rs.getString("side"));
                    int x = rs.getInt("x");
                    int y = rs.getInt("y");
                    board.put(Point.of(x, y), pieceType.create(side));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("기물 정보를 불러오는 데 실패했습니다.", e);
        }
        return board;
    }
}

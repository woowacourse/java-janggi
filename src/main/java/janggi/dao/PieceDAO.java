package janggi.dao;

import janggi.DBConnection;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceCache;
import janggi.domain.piece.Position;
import janggi.domain.piece.Team;
import java.sql.*;
import java.util.*;

public class PieceDAO {
    public void savePieces(final int snapshotId, final Map<Position, Piece> pieces) {
        String sql = "INSERT INTO Piece (snapshot_id, piece_name, position_x, position_y, team) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (Map.Entry<Position, Piece> entry : pieces.entrySet()) {
                Position pos = entry.getKey();
                Piece piece = entry.getValue();

                pstmt.setInt(1, snapshotId);
                pstmt.setString(2, piece.getName());
                pstmt.setInt(3, pos.x());
                pstmt.setInt(4, pos.y());
                pstmt.setString(5, piece.getTeam().toString());

                pstmt.addBatch();
            }
            pstmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Map<Position, Piece> loadPieces(final int snapshotId) {
        String sql = "SELECT piece_name, position_x, position_y, team FROM Piece WHERE snapshot_id = ?";
        Map<Position, Piece> board = new HashMap<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, snapshotId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String pieceName = rs.getString("piece_name");
                int x = rs.getInt("position_x");
                int y = rs.getInt("position_y");
                Team team = Team.valueOf(rs.getString("team"));

                Position position = new Position(x, y);
                Piece piece = PieceCache.getPiece(pieceName, team);

                board.put(position, piece);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return board;
    }
}
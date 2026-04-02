package janggi.dao;

import janggi.db.SQLManager;
import janggi.dto.PieceDto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class Piece {
    private final SQLManager sqlManager;

    public Piece(SQLManager sqlManager) {
        this.sqlManager = sqlManager;
    }

    public void initTable() {
        String sql =
        """
        CREATE TABLE IF NOT EXISTS Piece (
            game_id INTEGER NOT NULL,
            x INTEGER NOT NULL,
            y INTEGER NOT NULL,
            piece_type TEXT NOT NULL,
            side TEXT NOT NULL,
            PRIMARY KEY (game_id, x, y),
            FOREIGN KEY (game_id) REFERENCES GameRoom(id) ON DELETE CASCADE
        )
        """;

        try (Connection conn = sqlManager.ensureConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            if (!conn.getAutoCommit()) conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<PieceDto> getAllPieces(int gameId) {
        List<PieceDto> pieces = new ArrayList<>();
        String sql = "SELECT * FROM Piece WHERE game_id = ?";

        try (Connection conn = sqlManager.ensureConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, gameId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    PieceDto pieceDto = new PieceDto(
                            rs.getInt("x"),
                            rs.getInt("y"),
                            rs.getString("piece_type"),
                            rs.getString("side")
                    );
                    pieces.add(pieceDto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pieces;
    }

    public void updatePiece(Connection conn, int gameId, int x, int y, String type, String side) {
        String sql = """
        INSERT INTO Piece (game_id, x, y, piece_type, side)
        VALUES (?, ?, ?, ?, ?)
        ON CONFLICT(game_id, x, y)
        DO UPDATE SET
            piece_type = excluded.piece_type,
            side = excluded.side
        """;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, gameId);
            pstmt.setInt(2, x);
            pstmt.setInt(3, y);
            pstmt.setString(4, type);
            pstmt.setString(5, side);

            pstmt.executeUpdate();

            if (!conn.getAutoCommit()) conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePiece(Connection conn, int gameId, int x, int y) {
        String sql = "DELETE FROM Piece WHERE game_id = ? AND x = ? AND y = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, gameId);
            pstmt.setInt(2, x);
            pstmt.setInt(3, y);

            pstmt.executeUpdate();
            if (!conn.getAutoCommit()) conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


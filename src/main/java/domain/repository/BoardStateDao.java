package domain.repository;

import domain.coordinate.Position;
import domain.piece.Piece;
import domain.piece.PieceFactory;
import domain.piece.PieceType;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class BoardStateDao {

    private static final String DELETE_BOARD_STATE_SQL = "DELETE FROM board_state WHERE game_id = ?";
    private static final String INSERT_BOARD_STATE_SQL = "INSERT INTO board_state (game_id, row_pos, col_pos, piece_type, side) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_PIECES_BY_GAME_ID_SQL = "SELECT * FROM board_state WHERE game_id = ?";

    public void resetBoard(Connection conn, Long gameId) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(DELETE_BOARD_STATE_SQL)) {
            pstmt.setLong(1, gameId);
            pstmt.executeUpdate();
        }
    }

    public void saveBoardState(Connection conn, Long gameId, Map<Position, Piece> board) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(INSERT_BOARD_STATE_SQL)) {
            for (Map.Entry<Position, Piece> entry : board.entrySet()) {
                Piece piece = entry.getValue();
                if (piece.getType() == PieceType.EMPTY) continue;

                pstmt.setLong(1, gameId);
                pstmt.setInt(2, entry.getKey().row());
                pstmt.setInt(3, entry.getKey().col());
                pstmt.setString(4, piece.getType().name());
                pstmt.setString(5, piece.getSide().name());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        }
    }

    public Map<Position, Piece> findPiecesByGameId(Connection conn, Long gameId) throws SQLException {
        Map<Position, Piece> pieceMap = new HashMap<>();

        try (PreparedStatement pstmt = conn.prepareStatement(SELECT_PIECES_BY_GAME_ID_SQL)) {
            pstmt.setLong(1, gameId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Position pos = Position.of(rs.getInt("col_pos"), rs.getInt("row_pos"));
                    Piece piece = PieceFactory.create(rs.getString("piece_type"), rs.getString("side"));
                    pieceMap.put(pos, piece);
                }
            }
        }
        return pieceMap;
    }
}

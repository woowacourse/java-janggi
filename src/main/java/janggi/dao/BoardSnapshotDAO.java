package janggi.dao;

import janggi.DBConnection;
import janggi.domain.board.Board;
import janggi.domain.piece.Team;
import janggi.entity.BoardSnapshot;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BoardSnapshotDAO {

    private final PieceDAO pieceDAO;

    public BoardSnapshotDAO(final PieceDAO pieceDAO) {
        this.pieceDAO = pieceDAO;
    }

    public BoardSnapshot loadLatestSnapshotOrNull(final int gameId) {
        String sql = "SELECT snapshot_id, game_id, turn, created_at FROM BoardSnapshot WHERE game_id = ? ORDER BY created_at DESC LIMIT 1";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, gameId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new BoardSnapshot(
                        rs.getInt("snapshot_id"),
                        rs.getInt("game_id"),
                        rs.getString("turn"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveBoardSnapshot(final int gameId, final Board board, final Team turn) {
        String sql = "INSERT INTO BoardSnapshot (game_id, turn) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, gameId);
            pstmt.setString(2, turn.getName());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                int snapshotId = rs.getInt(1);
                pieceDAO.savePieces(snapshotId, board.getBoard());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
package janggi.dao;

import janggi.DBConnection;
import janggi.domain.board.Board;

import janggi.domain.piece.Team;
import janggi.entity.BoardSnapshot;
import java.sql.*;

public class BoardSnapshotDAO {

    private final PieceDAO pieceDAO;

    public BoardSnapshotDAO(final PieceDAO pieceDAO) {
        this.pieceDAO = pieceDAO;
    }

    public BoardSnapshot loadLatestSnapshot(final int gameId) {
        String sql = "SELECT snapshot_id, game_id, turn, snapshot_time FROM BoardSnapshot WHERE game_id = ? ORDER BY snapshot_time DESC LIMIT 1";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, gameId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new BoardSnapshot(
                        rs.getInt("snapshot_id"),
                        rs.getInt("game_id"),
                        rs.getString("turn"),
                        rs.getTimestamp("snapshot_time").toLocalDateTime()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveBoardSnapshot(final Board board, final Team turn, final int gameId) {
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
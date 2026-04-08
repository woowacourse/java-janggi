package dao;

import dto.PieceSnapshot;
import infrastructure.TransactionContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PieceMoveDao {

    public long insertMove(long gameId, int moveOrder,
                           int fromRow, int fromCol, int toRow, int toCol) throws SQLException {
        String sql = "INSERT INTO move (game_id, move_order, from_row, from_col, to_row, to_col) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = TransactionContext.getPreparedStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, gameId);
            ps.setInt(2, moveOrder);
            ps.setInt(3, fromRow);
            ps.setInt(4, fromCol);
            ps.setInt(5, toRow);
            ps.setInt(6, toCol);
            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                return keys.getLong(1);
            }
            throw new SQLException("move INSERT 후 id 획득 실패");
        }
    }

    public void insertMovePieces(long moveId, PieceSnapshot pieceSnapshot) throws SQLException {
        String sql = "INSERT INTO move_piece (move_id, name, team, pos_row, pos_col) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = TransactionContext.getPreparedStatement(sql)) {
            ps.setLong(1, moveId);
            ps.setString(2, pieceSnapshot.name());
            ps.setString(3, pieceSnapshot.team());
            ps.setInt(4, pieceSnapshot.row());
            ps.setInt(5, pieceSnapshot.col());
            ps.addBatch();

            ps.executeBatch();
        }
    }

    public ResultSet findLatestMovePieces(long gameId) throws SQLException {
        String sql = """
                SELECT mp.name, mp.team, mp.row, mp.col
                FROM move_piece mp
                JOIN move m ON mp.move_id = m.id
                WHERE m.game_id = ?
                  AND m.move_order = (SELECT MAX(move_order) FROM move WHERE game_id = ?)
                """;

        PreparedStatement ps = TransactionContext.getPreparedStatement(sql);
        ps.setLong(1, gameId);
        ps.setLong(2, gameId);
        return ps.executeQuery();
    }

    public int findCurrentMoveOrder(long gameId) throws SQLException {
        String sql = "SELECT COALESCE(MAX(move_order), 0) FROM move WHERE game_id = ?";

        try (PreparedStatement ps = TransactionContext.getPreparedStatement(sql)) {
            ps.setLong(1, gameId);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        }
    }
}

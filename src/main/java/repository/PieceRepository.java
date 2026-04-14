package repository;

import domain.CellSnapshot;
import domain.coordinate.Position;
import domain.piece.Piece;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class PieceRepository {

    public Map<Position, Piece> getPiecesPosition() {
        String sql = "SELECT col_num, row_num, piece_type, side FROM piece";
        try (Connection connection = Database.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet resultSet = pstmt.executeQuery()) {
            return PieceMapper.toPositionPiece(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updatePiecesPosition(Connection connection, Map<Position, CellSnapshot> pieces) throws SQLException {
        String sql = "MERGE INTO piece (col_num, row_num, piece_type, side) KEY (col_num, row_num) VALUES (?, ?, ?, ?)";
        for (Map.Entry<Position, CellSnapshot> entry : pieces.entrySet()) {
            Position position = entry.getKey();
            CellSnapshot cellSnapshot = entry.getValue();

            updatePiecePosition(connection, sql, cellSnapshot, position);
        }
    }

    public void resetAll(Connection connection) throws SQLException {
        String sql = "TRUNCATE TABLE piece";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.executeUpdate();
        }
    }

    private static void updatePiecePosition(Connection connection, String sql, CellSnapshot cellSnapshot,
                                            Position position) throws SQLException {
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, position.row());
            pstmt.setInt(2, position.col());
            pstmt.setString(3, cellSnapshot.typeName());
            pstmt.setString(4, cellSnapshot.sideName());

            pstmt.executeUpdate();
        }
    }
}

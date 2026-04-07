package repository;

import domain.CellSnapshot;
import domain.coordinate.Position;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class PieceRepository {

    public void updatePiecesPosition(Map<Position, CellSnapshot> pieces) {
        try (Connection connection = Database.getConnection()) {
            String sql = "MERGE INTO piece (col_num, row_num, piece_type, side) KEY (col_num, row_num) VALUES (?, ?, ?, ?)";
            try {
                connection.setAutoCommit(false);
                for (Map.Entry<Position, CellSnapshot> entry : pieces.entrySet()) {
                    Position position = entry.getKey();
                    CellSnapshot cellSnapshot = entry.getValue();

                    updatePiecePosition(connection, sql, cellSnapshot, position);
                }
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(e);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void updatePiecePosition(Connection connection, String sql, CellSnapshot cellSnapshot,
                                            Position position)
            throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, position.col());
        pstmt.setInt(2, position.row());
        pstmt.setString(3, cellSnapshot.typeName());
        pstmt.setString(4, cellSnapshot.sideName());

        pstmt.executeUpdate();

    }
}

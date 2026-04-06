package janggi.repository;

import janggi.entity.PieceEntity;
import janggi.exception.database.GameLoadException;
import janggi.exception.database.PieceDeletionException;
import janggi.exception.database.PieceInitializationException;
import janggi.exception.database.PieceMovementException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcPieceRepository implements PieceRepository {

    @Override
    public void saveAll(Connection conn, List<PieceEntity> entities) {
        String sql = "INSERT INTO Piece (game_id, type, `row`, col) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (PieceEntity entity : entities) {
                pstmt.setInt(1, entity.getGameId());
                pstmt.setString(2, entity.getType());
                pstmt.setInt(3, entity.getRow());
                pstmt.setInt(4, entity.getColumn());

                pstmt.addBatch();
            }

            pstmt.executeBatch();

        } catch (SQLException e) {
            throw new PieceInitializationException(e);
        }
    }

    @Override
    public void updatePosition(Connection conn, int gameId, int oldRow, int oldCol, int newRow, int newCol) {
        String sql = "UPDATE Piece SET `row` = ?, col = ? WHERE game_id = ? AND `row` = ? AND col = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, newRow);
            pstmt.setInt(2, newCol);

            pstmt.setInt(3, gameId);
            pstmt.setInt(4, oldRow);
            pstmt.setInt(5, oldCol);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new PieceMovementException(e);
        }
    }

    @Override
    public void deleteByPosition(Connection conn, int gameId, int row, int col) {
        String sql = "DELETE FROM Piece WHERE game_id = ? AND `row` = ? AND col = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, gameId);
            pstmt.setInt(2, row);
            pstmt.setInt(3, col);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new PieceDeletionException(e);
        }
    }

    @Override
    public List<PieceEntity> findAllByGameId(Connection conn, int gameId) {
        String sql = "SELECT * FROM Piece WHERE game_id = ?";
        List<PieceEntity> pieces = new ArrayList<>();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, gameId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    pieces.add(new PieceEntity(
                            rs.getInt("game_id"),
                            rs.getString("type"),
                            rs.getInt("row"),
                            rs.getInt("col")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new GameLoadException(e);
        }
        return pieces;
    }
}

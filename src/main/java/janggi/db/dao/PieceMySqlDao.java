package janggi.db.dao;

import janggi.db.entity.PieceEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PieceMySqlDao implements PieceDao {

    public List<PieceEntity> selectPiecesByGameId(Connection connection, int gameId) throws SQLException {
        List<PieceEntity> pieceEntities = new ArrayList<>();

        String sql = "SELECT piece_type, side, row_index, col_index FROM piece WHERE game_id = ? ";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, gameId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String pieceType = rs.getString("piece_type");
                    String side = rs.getString("side");
                    int rowIndex = rs.getInt("row_index");
                    int colIndex = rs.getInt("col_index");
                    PieceEntity pieceEntity = new PieceEntity(pieceType, side, rowIndex, colIndex, gameId);
                    pieceEntities.add(pieceEntity);
                }
            }
        }

        return pieceEntities;
    }

    @Override
    public void insertNewPieces(Connection connection, List<PieceEntity> pieceEntities) throws SQLException {
        String sql = "INSERT INTO piece (piece_type, side, row_index, col_index, game_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            for (PieceEntity entity : pieceEntities) {
                pstmt.setString(1, entity.pieceType());
                pstmt.setString(2, entity.side());
                pstmt.setInt(3, entity.rowIndex());
                pstmt.setInt(4, entity.colIndex());
                pstmt.setInt(5, entity.gameId());

                pstmt.addBatch();
            }
            pstmt.executeBatch();
        }
    }

    @Override
    public void deletePiecesByGameId(Connection connection, int gameId) throws SQLException {
        String sql = "DELETE FROM piece WHERE game_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, gameId);
            pstmt.executeUpdate();
        }
    }
}

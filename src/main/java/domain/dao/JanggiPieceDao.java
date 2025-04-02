package domain.dao;

import domain.piece.Piece;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JanggiPieceDao {

    private Connection connection;

    public JanggiPieceDao(Connection connection) {
        this.connection = connection;
    }

    public void deletePiecesByGameId(int gameId) {
        String deletePiecesSQL = "DELETE FROM piece WHERE game_id = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(deletePiecesSQL)) {
            preparedStatement.setInt(1, gameId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] PIECE 삭제 실패");
        }
    }

    public List<Integer> addPiecesBatch(int gameId, List<Piece> pieces) {
        String sql = "INSERT INTO piece(country, piece_type, game_id) values(?,?,?);";
        List<Integer> pieceIds = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            for (Piece piece : pieces) {
                stmt.setString(1, piece.getCountry().getName());
                stmt.setString(2, piece.getPieceType().getName());
                stmt.setInt(3, gameId);
                stmt.addBatch();
            }

            stmt.executeBatch();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                while (rs.next()) {
                    pieceIds.add(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("[ERROR] Batch 실패");
        }

        return pieceIds;
    }
}

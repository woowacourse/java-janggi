package janggi.infrastructure.dao;

import janggi.domain.piece.Piece;
import janggi.domain.space.Position;
import janggi.infrastructure.dao.dto.PieceEntity;
import janggi.infrastructure.db.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PieceDao {

    public void insertPieces(Connection connection, Long gameId, Map<Position, Piece> board) throws SQLException {
        String sql = "INSERT INTO piece (game_id, x, y, side, piece_type) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (Map.Entry<Position, Piece> entry : board.entrySet()) {
                Position position = entry.getKey();
                Piece piece = entry.getValue();

                preparedStatement.setLong(1, gameId);
                preparedStatement.setInt(2, position.getX());
                preparedStatement.setInt(3, position.getY());
                preparedStatement.setString(4, piece.getSide().name());
                preparedStatement.setString(5, piece.getType().name());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
    }

    public List<PieceEntity> findAllByGameId(Long gameId) {
        String sql = "SELECT * FROM piece WHERE game_id = ?";
        List<PieceEntity> pieces = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, gameId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    pieces.add(new PieceEntity(
                            resultSet.getLong("id"),
                            resultSet.getLong("game_id"),
                            resultSet.getInt("x"),
                            resultSet.getInt("y"),
                            resultSet.getString("side"),
                            resultSet.getString("piece_type")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("기물 목록 조회 중 DB 오류 발생", e);
        }
        return pieces;
    }

    public void deleteAllByGameId(Connection connection, Long gameId) throws SQLException {
        String sql = "DELETE FROM piece WHERE game_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, gameId);
            preparedStatement.executeUpdate();
        }
    }
}

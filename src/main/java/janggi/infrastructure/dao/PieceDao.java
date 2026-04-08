package janggi.infrastructure.dao;

import janggi.infrastructure.dao.dto.PieceEntity;
import janggi.infrastructure.db.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PieceDao {

    public void insertPiece(Long gameId, int x, int y, String side, String pieceType) {
        String sql = "INSERT INTO piece (game_id, x, y, side, piece_type) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, gameId);
            preparedStatement.setInt(2, x);
            preparedStatement.setInt(3, y);
            preparedStatement.setString(4, side);
            preparedStatement.setString(5, pieceType);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("기물 정보 저장 중 DB 오류 발생", e);
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

    public void deleteAllByGameId(Long gameId) {
        String sql = "DELETE FROM piece WHERE game_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, gameId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("기물 삭제 중 DB 오류 발생", e);
        }
    }
}

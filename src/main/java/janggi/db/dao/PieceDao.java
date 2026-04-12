package janggi.db.dao;

import janggi.db.DbConnector;
import janggi.db.entity.PieceEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class PieceDao {

    public void saveAll(List<PieceEntity> pieces) {
        String sql = "INSERT INTO pieces (game_id, x, y, piece_type, team) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DbConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            for (PieceEntity piece : pieces) {
                preparedStatement.setLong(1, piece.getGameId());
                preparedStatement.setInt(2, piece.getX());
                preparedStatement.setInt(3, piece.getY());
                preparedStatement.setString(4, piece.getPieceType().name());
                preparedStatement.setString(5, piece.getTeam().name());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 기물 저장 중 오류가 발생했습니다", e);
        }
    }

    public void deleteAllByGameId(Long gameId) {
        String sql = "DELETE FROM pieces WHERE game_id = ?";

        try (Connection conn = DbConnector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setLong(1, gameId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 기물 삭제 중 오류가 발생했습니다", e);
        }
    }
}

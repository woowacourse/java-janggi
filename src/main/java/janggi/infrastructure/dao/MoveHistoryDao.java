package janggi.infrastructure.dao;

import janggi.domain.space.Position;
import janggi.infrastructure.dao.dto.MoveEntity;
import janggi.infrastructure.db.ConnectionContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MoveHistoryDao {
    private Connection getConnection() {
        return ConnectionContext.get();
    }

    public void insertMove(Long gameId, Position source, Position target) throws SQLException {
        String sql = "INSERT INTO move_history (game_id, source_x, source_y, target_x, target_y) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.setLong(1, gameId);
            preparedStatement.setInt(2, source.getX());
            preparedStatement.setInt(3, source.getY());
            preparedStatement.setInt(4, target.getX());
            preparedStatement.setInt(5, target.getY());
            preparedStatement.executeUpdate();
        }
    }

    public List<MoveEntity> findAllByGameId(Long gameId) throws SQLException {
        String sql = "SELECT source_x, source_y, target_x, target_y FROM move_history WHERE game_id = ? ORDER BY id ASC";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.setLong(1, gameId);
            return extractMoves(preparedStatement);
        }
    }

    private List<MoveEntity> extractMoves(PreparedStatement statement) throws SQLException {
        try (ResultSet resultSet = statement.executeQuery()) {
            List<MoveEntity> moves = new ArrayList<>();
            while (resultSet.next()) {
                moves.add(new MoveEntity(
                        resultSet.getInt("source_x"),
                        resultSet.getInt("source_y"),
                        resultSet.getInt("target_x"),
                        resultSet.getInt("target_y")
                ));
            }
            return moves;
        }
    }
}

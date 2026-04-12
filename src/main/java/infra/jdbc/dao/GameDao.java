package infra.jdbc.dao;

import domain.game.GameStatus;
import domain.pieces.Side;
import infra.jdbc.GameEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

public class GameDao {

    public long insert(Connection connection, GameEntity gameEntity) throws SQLException {
        String sql = "INSERT INTO janggi_game (current_turn, status, winner, created_at, updated_at) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, gameEntity.currentTurn().name());
            statement.setString(2, gameEntity.status().name());
            statement.setString(3, gameEntity.winner() == null ? null : gameEntity.winner().name());
            statement.setTimestamp(4, Timestamp.valueOf(gameEntity.createdAt()));
            statement.setTimestamp(5, Timestamp.valueOf(gameEntity.updatedAt()));
            statement.executeUpdate();

            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return resultSet.getLong(1);
                }
                throw new SQLException("장기 게임 ID 생성에 실패했습니다.");
            }
        }
    }

    public Optional<GameMetadata> findLatestRunningGame(Connection connection) throws SQLException {
        String sql = "SELECT * FROM janggi_game WHERE status = ? ORDER BY created_at DESC LIMIT 1";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, GameStatus.RUNNING.name());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(new GameMetadata(
                            resultSet.getLong("game_id"),
                            Side.valueOf(resultSet.getString("current_turn")),
                            GameStatus.valueOf(resultSet.getString("status")),
                            resultSet.getString("winner") == null ? null : Side.valueOf(resultSet.getString("winner")),
                            resultSet.getTimestamp("created_at").toLocalDateTime(),
                            resultSet.getTimestamp("updated_at").toLocalDateTime()
                    ));
                }
                return Optional.empty();
            }
        }
    }

    public void update(
            Connection connection,
            Long gameId,
            Side currentTurn,
            GameStatus status,
            Side winner,
            LocalDateTime updatedAt
    ) throws SQLException {
        String sql = "UPDATE janggi_game SET current_turn = ?, status = ?, winner = ?, updated_at = ? WHERE game_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, currentTurn.name());
            statement.setString(2, status.name());
            statement.setString(3, winner == null ? null : winner.name());
            statement.setTimestamp(4, Timestamp.valueOf(updatedAt));
            statement.setLong(5, gameId);
            statement.executeUpdate();
        }
    }

    public record GameMetadata(
            Long gameId,
            Side currentTurn,
            GameStatus status,
            Side winner,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
    }
}

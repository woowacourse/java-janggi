package janggi.repository.dao;

import janggi.repository.data.GameData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class GameDao {
    private static final String PLAYING_STATUS = "PLAYING";

    public long save(Connection connection, GameData gameData) throws SQLException {
        String sql = "INSERT INTO games(turn, status) VALUES(?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, gameData.turn());
            statement.setString(2, gameData.status());
            statement.executeUpdate();
            return findGeneratedGameId(statement);
        }
    }

    public Optional<GameData> findPlayingGame(Connection connection) throws SQLException {
        String sql = "SELECT id, turn, status FROM games WHERE status = ? ORDER BY id DESC LIMIT 1";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, PLAYING_STATUS);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return Optional.empty();
                }
                return Optional.of(new GameData(
                        resultSet.getLong("id"),
                        resultSet.getString("turn"),
                        resultSet.getString("status")
                ));
            }
        }
    }

    public void update(Connection connection, GameData gameData) throws SQLException {
        String sql = "UPDATE games SET turn = ?, status = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, gameData.turn());
            statement.setString(2, gameData.status());
            statement.setLong(3, gameData.id());
            statement.executeUpdate();
        }
    }

    private long findGeneratedGameId(PreparedStatement statement) throws SQLException {
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (!generatedKeys.next()) {
                throw new IllegalStateException("저장된 게임 id를 찾을 수 없습니다.");
            }
            return generatedKeys.getLong(1);
        }
    }
}

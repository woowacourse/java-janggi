package infra.jdbc.dao;

import domain.game.GameStatus;
import domain.pieces.Side;
import infra.jdbc.exception.JdbcRepositoryException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import infra.jdbc.SavedGameDto;

public class GameDao {
    private static final String INSERT_GAME_SQL = """
            INSERT INTO janggi_game (
                current_turn,
                status,
                winner,
                created_at,
                updated_at
            ) VALUES (?, ?, ?, ?, ?)
            """;

    private static final String FIND_LATEST_RUNNING_GAME_SQL = """
            SELECT game_id, current_turn, status, winner, created_at, updated_at
            FROM janggi_game
            WHERE status = ?
            ORDER BY updated_at DESC, game_id DESC
            LIMIT 1
            """;

    private static final String UPDATE_GAME_SQL = """
            UPDATE janggi_game
            SET current_turn = ?, status = ?, winner = ?, updated_at = ?
            WHERE game_id = ?
            """;

    public long insert(Connection connection, SavedGameDto savedGameDto) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_GAME_SQL,
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, savedGameDto.currentTurn().name());
            statement.setString(2, savedGameDto.status().name());
            if (savedGameDto.winner() == null) {
                statement.setNull(3, java.sql.Types.VARCHAR);
            } else {
                statement.setString(3, savedGameDto.winner().name());
            }
            statement.setTimestamp(4, Timestamp.valueOf(savedGameDto.createdAt()));
            statement.setTimestamp(5, Timestamp.valueOf(savedGameDto.updatedAt()));
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                }
            }
        }

        throw new JdbcRepositoryException("저장한 장기 게임의 식별자를 조회하지 못했습니다.");
    }

    public Optional<GameMetadata> findLatestRunningGame(Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(FIND_LATEST_RUNNING_GAME_SQL)) {
            statement.setString(1, GameStatus.RUNNING.name());

            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return Optional.empty();
                }

                return Optional.of(new GameMetadata(
                        resultSet.getLong("game_id"),
                        Side.valueOf(resultSet.getString("current_turn")),
                        GameStatus.valueOf(resultSet.getString("status")),
                        toWinner(resultSet.getString("winner")),
                        resultSet.getTimestamp("created_at").toLocalDateTime(),
                        resultSet.getTimestamp("updated_at").toLocalDateTime()
                ));
            }
        }
    }

    public void update(
            Connection connection,
            long gameId,
            Side currentTurn,
            GameStatus status,
            Side winner,
            LocalDateTime updatedAt
    ) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_GAME_SQL)) {
            statement.setString(1, currentTurn.name());
            statement.setString(2, status.name());
            if (winner == null) {
                statement.setNull(3, java.sql.Types.VARCHAR);
            } else {
                statement.setString(3, winner.name());
            }
            statement.setTimestamp(4, Timestamp.valueOf(updatedAt));
            statement.setLong(5, gameId);
            statement.executeUpdate();
        }
    }

    private Side toWinner(String winner) {
        if (winner == null) {
            return null;
        }
        return Side.valueOf(winner);
    }

    public record GameMetadata(
            long gameId,
            Side currentTurn,
            GameStatus status,
            Side winner,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
    }
}

package janggi.persistence;

import janggi.domain.game.GameManager;
import janggi.domain.game.Side;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Optional;

public class JanggiGameRepository implements GameRepository {

    @Override
    public Optional<Long> findActiveGameId(Connection connection) throws SQLException {
        String sql = "select game_id from game where is_finished = false order by created_at desc limit 1";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            return extractActiveGameId(statement);
        }
    }

    private Optional<Long> extractActiveGameId(PreparedStatement statement) throws SQLException {
        try (ResultSet resultSet = statement.executeQuery()) {
            return mapToGameIdOptional(resultSet);
        }
    }

    private Optional<Long> mapToGameIdOptional(ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return Optional.empty();
        }
        return Optional.of(resultSet.getLong("game_id"));
    }

    @Override
    public long insertGame(Connection connection, GameManager gameManager) throws SQLException {
        String sql = "insert into game (cho_player_name, han_player_name, current_turn) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            return executeInsertAndGetId(statement, gameManager);
        }
    }

    private long executeInsertAndGetId(PreparedStatement statement, GameManager gameManager) throws SQLException {
        bindInsertParameters(statement, gameManager);
        statement.executeUpdate();
        return extractGeneratedId(statement);
    }

    private void bindInsertParameters(PreparedStatement statement, GameManager gameManager) throws SQLException {
        Map<Side, String> playersInfo = gameManager.getPlayersInfo();
        String choPlayerName = playersInfo.get(Side.CHO);
        String hanPlayerName = playersInfo.get(Side.HAN);
        statement.setString(1, choPlayerName);
        statement.setString(2, hanPlayerName);
        statement.setString(3, gameManager.getCurrentSide().name());
    }

    private long extractGeneratedId(PreparedStatement statement) throws SQLException {
        try (ResultSet resultSet = statement.getGeneratedKeys()) {
            return mapToGeneratedId(resultSet);
        }
    }

    private long mapToGeneratedId(ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            throw new SQLException("생성된 게임의 ID가 조회되지 않습니다.");
        }
        return resultSet.getLong(1);
    }
}

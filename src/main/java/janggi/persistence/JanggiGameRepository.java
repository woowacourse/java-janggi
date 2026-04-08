package janggi.persistence;

import janggi.domain.game.GameManager;
import janggi.domain.game.Side;
import janggi.dto.GameSessionDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JanggiGameRepository implements GameRepository {

    @Override
    public List<GameSessionDTO> findAllActiveGames(Connection connection) throws SQLException {
        String sql = "select game_id, cho_player_name, han_player_name, current_turn, created_at " +
                "from game where is_finished = false order by created_at desc";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            return executeFindAllActiveGames(statement);
        }
    }

    private List<GameSessionDTO> executeFindAllActiveGames(PreparedStatement statement) throws SQLException {
        try (ResultSet resultSet = statement.executeQuery()) {
            return mapToGameSessionList(resultSet);
        }
    }

    private List<GameSessionDTO> mapToGameSessionList(ResultSet resultSet) throws SQLException {
        List<GameSessionDTO> sessions = new ArrayList<>();
        while (resultSet.next()) {
            sessions.add(mapToSingleSession(resultSet));
        }
        return sessions;
    }

    private GameSessionDTO mapToSingleSession(ResultSet resultSet) throws SQLException {
        return new GameSessionDTO(
                resultSet.getLong("game_id"),
                resultSet.getString("cho_player_name"),
                resultSet.getString("han_player_name"),
                resultSet.getString("current_turn"),
                resultSet.getObject("created_at", LocalDateTime.class)
        );
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

    @Override
    public GameSessionDTO findByGameId(Connection connection, long gameId) throws SQLException {
        String sql = "select game_id, cho_player_name, han_player_name, current_turn, created_at from game where game_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            return executeFindById(statement, gameId);
        }
    }

    private GameSessionDTO executeFindById(PreparedStatement statement, long gameId) throws SQLException {
        statement.setLong(1, gameId);
        try (ResultSet resultSet = statement.executeQuery()) {
            resultSet.next();
            return mapToSingleSession(resultSet);
        }
    }

    @Override
    public void updateTurn(Connection connection, long gameId, GameManager gameManager) throws SQLException {
        String sql = "update game set current_turn = ? where game_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, gameManager.getCurrentSide().name());
            statement.setLong(2, gameId);
            executeAndValidateUpdate(statement);
        }
    }

    @Override
    public void updateIsFinished(Connection connection, long gameId, boolean finished) throws SQLException {
        String sql = "update game set is_finished = ? where game_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBoolean(1, finished);
            statement.setLong(2, gameId);
            executeAndValidateUpdate(statement);
        }
    }

    private void executeAndValidateUpdate(PreparedStatement statement) throws SQLException {
        int affectedRows = statement.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("업데이트 대상 게임을 찾을 수 없습니다.");
        }
    }
}

package janggi.persistence.dao;

import janggi.domain.game.GameManager;
import janggi.domain.game.Side;
import janggi.dto.GameSessionDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JdbcGameDao implements GameDao {

    @Override
    public long insert(Connection connection, GameManager gameManager) throws SQLException {
        String sql = "insert into game (cho_player_name, han_player_name, current_turn) values (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            executeSave(statement, gameManager);
            return extractGeneratedId(statement);
        }
    }

    @Override
    public void update(Connection connection, GameManager gameManager) throws SQLException {
        String sql = "update game set cho_player_name = ?, han_player_name = ?, current_turn = ? where game_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(4, gameManager.getId());
            executeSave(statement, gameManager);
        }
    }

    private void executeSave(PreparedStatement statement, GameManager gameManager) throws SQLException {
        bindInsertParameters(statement, gameManager);
        executeAndValidateUpdate(statement);
    }

    private void bindInsertParameters(PreparedStatement statement, GameManager gameManager) throws SQLException {
        Map<Side, String> playersInfo = gameManager.getPlayersInfo();
        statement.setString(1, playersInfo.get(Side.CHO));
        statement.setString(2, playersInfo.get(Side.HAN));
        statement.setString(3, gameManager.currentPlayer().side().name());
    }

    private void executeAndValidateUpdate(PreparedStatement statement) throws SQLException {
        int affectedRows = statement.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("업데이트 대상 게임을 찾을 수 없습니다.");
        }
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
    public GameSessionDto findById(Connection connection, long gameId) throws SQLException {
        String sql = "select game_id, cho_player_name, han_player_name, current_turn, created_at from game where game_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            return executeFindById(statement, gameId);
        }
    }

    private GameSessionDto executeFindById(PreparedStatement statement, long gameId) throws SQLException {
        statement.setLong(1, gameId);
        try (ResultSet resultSet = statement.executeQuery()) {
            resultSet.next();
            return mapToGameSessionDTO(resultSet);
        }
    }

    @Override
    public List<GameSessionDto> findAllActive(Connection connection) throws SQLException {
        String sql = "select game_id, cho_player_name, han_player_name, current_turn, created_at from game where is_finished = false order by created_at desc";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            return executeFindAllActive(statement);
        }
    }

    private List<GameSessionDto> executeFindAllActive(PreparedStatement statement) throws SQLException {
        try (ResultSet resultSet = statement.executeQuery()) {
            return mapToGameSessionList(resultSet);
        }
    }

    private List<GameSessionDto> mapToGameSessionList(ResultSet resultSet) throws SQLException {
        List<GameSessionDto> sessions = new ArrayList<>();
        while (resultSet.next()) {
            sessions.add(mapToGameSessionDTO(resultSet));
        }
        return sessions;
    }

    private GameSessionDto mapToGameSessionDTO(ResultSet resultSet) throws SQLException {
        return new GameSessionDto(
                resultSet.getLong("game_id"),
                resultSet.getString("cho_player_name"),
                resultSet.getString("han_player_name"),
                resultSet.getString("current_turn"),
                resultSet.getObject("created_at", LocalDateTime.class)
        );
    }
}

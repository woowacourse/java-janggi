package janggi.repository;

import janggi.domain.piece.Camp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class GameStateRepository {

    private static final String SELECT_CURRENT_TURN = """
            select current_turn
            from game_state
            where game_id = ?
            """;
    private static final String SELECT_ALL_GAME_ID = """
            select game_id
            from game_state
            order by game_id asc
            """;
    private static final String UPDATE_GAME_STATE = """
            update game_state
            set current_turn = ?
            where game_id = ?
            """;
    private static final String INSERT_GAME_STATE = """
            insert into game_state (game_id, current_turn)
            values (?, ?)
            """;
    private static final String CREATE_NEW_GAME_STATE = """
            insert into game_state (current_turn)
            values (?)
            """;
    private static final String DELETE_GAME_STATE = """
            delete from game_state
            where game_id = ?
            """;
    private static final String CANNOT_FIND_GAME = "[ERROR] 생성된 게임방 번호를 가져올 수 없습니다.";

    public List<Long> findAllIds(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SELECT_ALL_GAME_ID);

        try (statement; ResultSet resultSet = statement.executeQuery()) {
            List<Long> gameIds = new ArrayList<>();
            findAllGameIds(resultSet, gameIds);

            return gameIds;
        }
    }

    private void findAllGameIds(ResultSet resultSet, List<Long> gameIds) throws SQLException {
        while (resultSet.next()) {
            gameIds.add(resultSet.getLong("game_id"));
        }
    }

    public Optional<Camp> findCurrentTurnByGameId(Connection connection, long gameId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SELECT_CURRENT_TURN);
        statement.setLong(1, gameId);

        try (statement; ResultSet resultSet = statement.executeQuery()) {
            if (!resultSet.next()) {
                return Optional.empty();
            }

            return Optional.of(Camp.valueOf(resultSet.getString("current_turn")));
        }
    }

    public void save(Connection connection, long gameId, Camp currentTurn) throws SQLException {
        if (update(connection, gameId, currentTurn) > 0) {
            return;
        }
        insert(connection, gameId, currentTurn);
    }

    public long createGame(Connection connection, Camp currentTurn) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(CREATE_NEW_GAME_STATE,
                Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, currentTurn.name());

        try (statement) {
            statement.executeUpdate();
            return getGeneratedKey(statement);
        }
    }

    private long getGeneratedKey(PreparedStatement statement) throws SQLException {
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            validateKeys(generatedKeys);
            return generatedKeys.getLong(1);
        }
    }

    private void validateKeys(ResultSet generatedKeys) throws SQLException {
        if (!generatedKeys.next()) {
            throw new IllegalStateException(CANNOT_FIND_GAME);
        }
    }

    public void deleteById(Connection connection, long gameId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_GAME_STATE)) {
            statement.setLong(1, gameId);
            statement.executeUpdate();
        }
    }

    private int update(Connection connection, long gameId, Camp currentTurn) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(UPDATE_GAME_STATE);
        statement.setString(1, currentTurn.name());
        statement.setLong(2, gameId);

        try (statement) {
            return statement.executeUpdate();
        }
    }

    private void insert(Connection connection, long gameId, Camp currentTurn) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(INSERT_GAME_STATE);
        statement.setLong(1, gameId);
        statement.setString(2, currentTurn.name());

        try (statement) {
            statement.executeUpdate();
        }
    }
}

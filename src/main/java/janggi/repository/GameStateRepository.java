package janggi.repository;

import janggi.domain.piece.Camp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public final class GameStateRepository {

    private static final String SELECT_CURRENT_TURN = """
            select current_turn
            from game_state
            where game_id = ?
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
    private static final String DELETE_GAME_STATE = """
            delete from game_state
            where game_id = ?
            """;

    public Optional<Camp> findById(Connection connection, long gameId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SELECT_GAME_STATE);
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

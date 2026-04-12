package janggi.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GameStateDao {

    private static final String CANNOT_FIND_GAME = "[ERROR] 생성된 게임방 번호를 가져올 수 없습니다.";
    private static final String GAME_STATE_UPDATE_FAILED = "[ERROR] 게임 상태가 수정되지 않았습니다.";
    private static final String GAME_STATE_ACCESS_FAILED = "[ERROR] 게임 상태 테이블 접근 중 문제가 발생했습니다.";

    public long create(Connection connection, String currentTurn) {
        try (PreparedStatement statement = connection.prepareStatement("""
                insert into game_state (current_turn)
                values (?)
                """, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, currentTurn);
            return createGame(statement);
        } catch (SQLException e) {
            throw new DataAccessException(GAME_STATE_ACCESS_FAILED, e);
        }
    }

    public List<Long> findAllIds(Connection connection) {
        try (PreparedStatement statement = connection.prepareStatement("""
                select game_id
                from game_state
                order by game_id asc
                """)) {
            return readGameIds(statement);
        } catch (SQLException e) {
            throw new DataAccessException(GAME_STATE_ACCESS_FAILED, e);
        }
    }

    public Optional<String> findCurrentTurn(Connection connection, long gameId) {
        try (PreparedStatement statement = connection.prepareStatement("""
                select current_turn
                from game_state
                where game_id = ?
                """)) {
            statement.setLong(1, gameId);
            return readCurrentTurn(statement);
        } catch (SQLException e) {
            throw new DataAccessException(GAME_STATE_ACCESS_FAILED, e);
        }
    }

    public void update(Connection connection, long gameId, String currentTurn) {
        try (PreparedStatement statement = connection.prepareStatement("""
                update game_state
                set current_turn = ?
                where game_id = ?
                """)) {
            statement.setString(1, currentTurn);
            statement.setLong(2, gameId);

            validateUpdatedRows(updateCurrentTurn(statement));
        } catch (SQLException e) {
            throw new DataAccessException(GAME_STATE_ACCESS_FAILED, e);
        }
    }

    public void delete(Connection connection, long gameId) {
        try (PreparedStatement statement = connection.prepareStatement("""
                delete from game_state
                where game_id = ?
                """)) {
            statement.setLong(1, gameId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(GAME_STATE_ACCESS_FAILED, e);
        }
    }

    private long createGame(PreparedStatement statement) throws SQLException {
        statement.executeUpdate();
        return getGeneratedKey(statement);
    }

    private List<Long> readGameIds(PreparedStatement statement) throws SQLException {
        try (ResultSet resultSet = statement.executeQuery()) {
            List<Long> gameIds = new ArrayList<>();
            addGameIds(resultSet, gameIds);

            return gameIds;
        }
    }

    private Optional<String> readCurrentTurn(PreparedStatement statement) throws SQLException {
        try (ResultSet resultSet = statement.executeQuery()) {
            if (!resultSet.next()) {
                return Optional.empty();
            }

            return Optional.of(resultSet.getString("current_turn"));
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

    private void validateUpdatedRows(int updatedRows) {
        if (updatedRows == 0) {
            throw new IllegalStateException(GAME_STATE_UPDATE_FAILED);
        }
    }

    private void addGameIds(ResultSet resultSet, List<Long> gameIds) throws SQLException {
        while (resultSet.next()) {
            gameIds.add(resultSet.getLong("game_id"));
        }
    }

    private int updateCurrentTurn(PreparedStatement statement) throws SQLException {
        return statement.executeUpdate();
    }
}

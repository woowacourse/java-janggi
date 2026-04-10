package janggi.dao;

import janggi.dto.GameRoom;
import janggi.dto.NewGameRoom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class JdbcGameRoomDao implements GameRoomDao {
    private static final String INSERT_SQL = """
            INSERT INTO game_room (turn, finished, cho_score, han_score)
            VALUES (?, ?, ?, ?)
            """;
    private static final String UPDATE_SQL = """
            UPDATE game_room
            SET turn = ?, finished = ?, cho_score = ?, han_score = ?
            WHERE id = ?
            """;
    private static final String FIND_BY_ID_SQL = """
            SELECT id, turn, finished, cho_score, han_score
            FROM game_room
            WHERE id = ?
            """;

    @Override
    public long save(NewGameRoom newGameRoom, Connection connection) {
        try (PreparedStatement statement = createSaveStatement(newGameRoom, connection)) {
            statement.executeUpdate();
            return readGeneratedId(statement);
        } catch (SQLException e) {
            throw new IllegalStateException("게임 방 저장 중 오류가 발생했습니다.", e);
        }
    }

    @Override
    public void update(GameRoom gameRoom, Connection connection) {
        try (PreparedStatement statement = createUpdateStatement(gameRoom, connection)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("game_room 수정 중 오류가 발생했습니다.", e);
        }
    }

    @Override
    public Optional<GameRoom> findById(long gameRoomId, Connection connection) {
        try (PreparedStatement statement = createFindByIdStatement(gameRoomId, connection)) {
            ResultSet resultSet = statement.executeQuery();
            return toGameRoom(resultSet);
        } catch (SQLException e) {
            throw new IllegalStateException("game_room 조회 중 오류가 발생했습니다.", e);
        }
    }

    private PreparedStatement createSaveStatement(NewGameRoom newGameRoom, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(INSERT_SQL, PreparedStatement.RETURN_GENERATED_KEYS);
        statement.setString(1, newGameRoom.turn());
        statement.setBoolean(2, newGameRoom.finished());
        statement.setDouble(3, newGameRoom.choScore());
        statement.setDouble(4, newGameRoom.hanScore());
        return statement;
    }

    private long readGeneratedId(PreparedStatement statement) throws SQLException {
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            validateGeneratedKeys(generatedKeys);
            return generatedKeys.getLong(1);
        }
    }

    private void validateGeneratedKeys(ResultSet generatedKeys) throws SQLException {
        if (!generatedKeys.next()) {
            throw new SQLException("게임방 ID를 생성하지 못했습니다.");
        }
    }

    private PreparedStatement createUpdateStatement(GameRoom gameRoom, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(UPDATE_SQL);
        statement.setString(1, gameRoom.turn());
        statement.setBoolean(2, gameRoom.finished());
        statement.setDouble(3, gameRoom.choScore());
        statement.setDouble(4, gameRoom.hanScore());
        statement.setLong(5, gameRoom.id());
        return statement;
    }

    private PreparedStatement createFindByIdStatement(long gameRoomId, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_SQL);
        statement.setLong(1, gameRoomId);
        return statement;
    }

    private Optional<GameRoom> toGameRoom(ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return Optional.empty();
        }
        return Optional.of(mapToGameRoom(resultSet));
    }

    private GameRoom mapToGameRoom(ResultSet resultSet) throws SQLException {
        return new GameRoom(
                resultSet.getLong("id"),
                resultSet.getString("turn"),
                resultSet.getBoolean("finished"),
                resultSet.getDouble("cho_score"),
                resultSet.getDouble("han_score")
        );
    }
}
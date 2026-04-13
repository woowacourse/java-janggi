package persistence;

import application.port.GameSessionRepository;
import application.port.StoredGameSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcGameSessionRepository implements GameSessionRepository {
    private static final String IN_PROGRESS = "IN_PROGRESS";
    private static final String SELECT_IN_PROGRESS_SESSION = """
            SELECT id
            FROM game_session
            WHERE status = ?
            ORDER BY updated_at DESC, id DESC
            LIMIT 1
            """;
    private static final String INSERT_GAME_SESSION = """
            INSERT INTO game_session (status, created_at, updated_at)
            VALUES (?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
            """;
    private static final String INSERT_GAME_COMMAND = """
            INSERT INTO game_command (game_session_id, sequence_number, raw_command, created_at)
            VALUES (?, ?, ?, CURRENT_TIMESTAMP)
            """;
    private static final String SELECT_RAW_COMMANDS = """
            SELECT raw_command
            FROM game_command
            WHERE game_session_id = ?
            ORDER BY sequence_number
            """;
    private static final String SELECT_NEXT_SEQUENCE_NUMBER = """
            SELECT COALESCE(MAX(sequence_number), 0) + 1
            FROM game_command
            WHERE game_session_id = ?
            """;
    private static final String UPDATE_GAME_SESSION = """
            UPDATE game_session
            SET updated_at = CURRENT_TIMESTAMP
            WHERE id = ?
            """;
    private static final String FINISH_GAME_SESSION = """
            UPDATE game_session
            SET status = ?, updated_at = CURRENT_TIMESTAMP
            WHERE id = ?
            """;
    private static final String FINISHED = "FINISHED";

    private final ConnectionProvider connectionProvider;

    public JdbcGameSessionRepository(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
        new GameSessionSchemaInitializer(connectionProvider).initialize();
    }

    @Override
    public Optional<StoredGameSession> findInProgress() {
        try (
                Connection connection = connectionProvider.getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_IN_PROGRESS_SESSION)
        ) {
            statement.setString(1, IN_PROGRESS);
            return findStoredGameSession(connection, statement);
        } catch (SQLException exception) {
            throw new IllegalStateException("[ERROR] 진행 중 게임 조회에 실패했습니다.", exception);
        }
    }

    @Override
    public long create() {
        try (
                Connection connection = connectionProvider.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        INSERT_GAME_SESSION,
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {
            statement.setString(1, IN_PROGRESS);
            statement.executeUpdate();
            return extractId(statement);
        } catch (SQLException exception) {
            throw new IllegalStateException("[ERROR] 게임 세션 저장에 실패했습니다.", exception);
        }
    }

    @Override
    public void appendCommand(long gameSessionId, String rawCommand) {
        try (Connection connection = connectionProvider.getConnection()) {
            insertCommand(connection, gameSessionId, rawCommand);
            touchSession(connection, gameSessionId);
        } catch (SQLException exception) {
            throw new IllegalStateException("[ERROR] 명령 저장에 실패했습니다.", exception);
        }
    }

    @Override
    public void finish(long gameSessionId) {
        try (
                Connection connection = connectionProvider.getConnection();
                PreparedStatement statement = connection.prepareStatement(FINISH_GAME_SESSION)
        ) {
            statement.setString(1, FINISHED);
            statement.setLong(2, gameSessionId);
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new IllegalStateException("[ERROR] 게임 세션 종료에 실패했습니다.", exception);
        }
    }

    private Optional<StoredGameSession> findStoredGameSession(
            Connection connection,
            PreparedStatement statement
    ) throws SQLException {
        try (ResultSet resultSet = statement.executeQuery()) {
            return toStoredGameSession(connection, resultSet);
        }
    }

    private Optional<StoredGameSession> toStoredGameSession(
            Connection connection,
            ResultSet resultSet
    ) throws SQLException {
        if (!resultSet.next()) {
            return Optional.empty();
        }
        long gameSessionId = currentSessionId(resultSet);
        return Optional.of(new StoredGameSession(gameSessionId, findRawCommands(connection, gameSessionId)));
    }

    private List<String> findRawCommands(Connection connection, long gameSessionId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_RAW_COMMANDS)) {
            statement.setLong(1, gameSessionId);
            return extractRawCommands(statement);
        }
    }

    private List<String> extractRawCommands(PreparedStatement statement) throws SQLException {
        List<String> rawCommands = new ArrayList<>();
        try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                rawCommands.add(resultSet.getString("raw_command"));
            }
        }
        return rawCommands;
    }

    private long extractId(PreparedStatement statement) throws SQLException {
        try (ResultSet resultSet = statement.getGeneratedKeys()) {
            validateGeneratedKey(resultSet);
            return resultSet.getLong(1);
        }
    }

    private void insertCommand(Connection connection, long gameSessionId, String rawCommand) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_GAME_COMMAND)) {
            statement.setLong(1, gameSessionId);
            statement.setInt(2, nextSequenceNumber(connection, gameSessionId));
            statement.setString(3, rawCommand);
            statement.executeUpdate();
        }
    }

    private int nextSequenceNumber(Connection connection, long gameSessionId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_NEXT_SEQUENCE_NUMBER)) {
            statement.setLong(1, gameSessionId);
            return extractSequenceNumber(statement);
        }
    }

    private int extractSequenceNumber(PreparedStatement statement) throws SQLException {
        try (ResultSet resultSet = statement.executeQuery()) {
            resultSet.next();
            return resultSet.getInt(1);
        }
    }

    private void touchSession(Connection connection, long gameSessionId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_GAME_SESSION)) {
            statement.setLong(1, gameSessionId);
            statement.executeUpdate();
        }
    }

    private long currentSessionId(ResultSet resultSet) throws SQLException {
        return resultSet.getLong("id");
    }

    private void validateGeneratedKey(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return;
        }
        throw new IllegalStateException("[ERROR] 게임 세션 식별자를 찾을 수 없습니다.");
    }
}

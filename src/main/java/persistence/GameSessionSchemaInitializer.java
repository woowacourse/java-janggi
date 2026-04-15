package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GameSessionSchemaInitializer {
    private static final String CREATE_GAME_SESSION_TABLE = """
            CREATE TABLE IF NOT EXISTS game_session (
                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                status VARCHAR(20) NOT NULL,
                created_at TIMESTAMP NOT NULL,
                updated_at TIMESTAMP NOT NULL
            )
            """;
    private static final String CREATE_GAME_COMMAND_TABLE = """
            CREATE TABLE IF NOT EXISTS game_command (
                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                game_session_id BIGINT NOT NULL,
                sequence_number INT NOT NULL,
                raw_command VARCHAR(20) NOT NULL,
                created_at TIMESTAMP NOT NULL,
                CONSTRAINT fk_game_command_session
                    FOREIGN KEY (game_session_id) REFERENCES game_session(id),
                CONSTRAINT uk_game_command_sequence
                    UNIQUE (game_session_id, sequence_number)
            )
            """;

    private final ConnectionProvider connectionProvider;

    public GameSessionSchemaInitializer(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public void initialize() {
        execute(CREATE_GAME_SESSION_TABLE);
        execute(CREATE_GAME_COMMAND_TABLE);
    }

    private void execute(String sql) {
        try (
                Connection connection = connectionProvider.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.execute();
        } catch (SQLException exception) {
            throw new IllegalStateException("[ERROR] 데이터베이스 스키마 초기화에 실패했습니다.", exception);
        }
    }
}

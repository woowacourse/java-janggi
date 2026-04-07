package janggi.config;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SchemaInitializer {
    private static final String INIT_SCHEMA_FAIL_MESSAGE = "DB 스키마 초기화 중 오류가 발생했습니다.";

    private static final String CREATE_GAME_ROOM_SQL = """
            CREATE TABLE IF NOT EXISTS game_room (
                id BIGINT NOT NULL AUTO_INCREMENT,
                turn VARCHAR(8) NOT NULL,
                finished BOOLEAN NOT NULL,
                cho_score DECIMAL(4, 1) NOT NULL,
                han_score DECIMAL(4, 1) NOT NULL,
                PRIMARY KEY (id)
            )
            """;

    private static final String CREATE_BOARD_PIECE_SQL = """
            CREATE TABLE IF NOT EXISTS board_piece (
                game_room_id BIGINT NOT NULL,
                row_pos INT NOT NULL,
                col_pos INT NOT NULL,
                piece_type VARCHAR(16) NOT NULL,
                side VARCHAR(8) NOT NULL,
                PRIMARY KEY (game_room_id, row_pos, col_pos),
                CONSTRAINT fk_board_piece_game_room
                    FOREIGN KEY (game_room_id)
                    REFERENCES game_room(id)
                    ON DELETE CASCADE
            )
            """;

    private final ConnectionManager connectionManager;

    public SchemaInitializer(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public void init() {
        createDatabaseIfNotExists();
        createTablesIfNotExists();
    }

    private void createDatabaseIfNotExists() {
        String sql = "CREATE DATABASE IF NOT EXISTS " + connectionManager.getDatabaseName();

        try (Connection connection = connectionManager.getServerConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            throw new IllegalStateException(INIT_SCHEMA_FAIL_MESSAGE, e);
        }
    }

    private void createTablesIfNotExists() {
        try (Connection connection = connectionManager.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(CREATE_GAME_ROOM_SQL);
            statement.execute(CREATE_BOARD_PIECE_SQL);
        } catch (SQLException e) {
            throw new IllegalStateException(INIT_SCHEMA_FAIL_MESSAGE, e);
        }
    }
}

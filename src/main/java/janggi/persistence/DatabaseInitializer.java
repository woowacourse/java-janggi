package janggi.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initialize(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            executeSchemaCreation(statement);
            executeDummyDataInsertionIfEmpty(statement);
        }
    }

    private static void executeSchemaCreation(Statement statement) throws SQLException {
        executeGameSchemaCreation(statement);
        executeBoardSchemaCreation(statement);
    }

    private static void executeGameSchemaCreation(Statement statement) throws SQLException {
        String createSql = """
                    CREATE TABLE IF NOT EXISTS GAME (
                        game_id IDENTITY PRIMARY KEY,
                        cho_player_name VARCHAR(50) NOT NULL,
                        han_player_name VARCHAR(50) NOT NULL,
                        current_turn VARCHAR(10) NOT NULL,
                        is_finished BOOLEAN DEFAULT FALSE,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                    )
                """;
        statement.execute(createSql);
    }

    private static void executeBoardSchemaCreation(Statement statement) throws SQLException {
        String createSql = """
                    CREATE TABLE IF NOT EXISTS BOARD (
                        game_id BIGINT NOT NULL,
                        side VARCHAR(10) NOT NULL,
                        piece_type VARCHAR(20) NOT NULL,
                        piece_number VARCHAR(10) NOT NULL,
                        row_index INT NOT NULL,
                        column_index INT NOT NULL,
                        PRIMARY KEY (game_id, side, piece_type, piece_number),
                        FOREIGN KEY (game_id) REFERENCES GAME(game_id) ON DELETE CASCADE
                    )
                """;
        statement.execute(createSql);
    }

    private static void executeDummyDataInsertionIfEmpty(Statement statement) throws SQLException {
        if (!hasExistingData(statement)) {
            insertDummyData(statement);
        }
    }

    private static boolean hasExistingData(Statement statement) throws SQLException {
        try (ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM GAME")) {
            resultSet.next();
            return resultSet.getInt(1) > 0;
        }
    }

    private static void insertDummyData(Statement statement) throws SQLException {
        String insertSql = "INSERT INTO GAME (cho_player_name, han_player_name, current_turn, is_finished) VALUES ('테스트초', '테스트한', 'CHO', FALSE)";
        statement.executeUpdate(insertSql);
    }
}

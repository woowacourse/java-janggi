package janggi.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseInitializer {

    public static void initialize(Connection connection) throws SQLException {
        executeGameSchemaCreation(connection);
        executeBoardSchemaCreation(connection);
    }

    private static void executeGameSchemaCreation(Connection connection) throws SQLException {
        String createGameSql = """
                    CREATE TABLE IF NOT EXISTS GAME (
                        game_id IDENTITY PRIMARY KEY,
                        cho_player_name VARCHAR(50) NOT NULL,
                        han_player_name VARCHAR(50) NOT NULL,
                        current_turn VARCHAR(10) NOT NULL,
                        is_finished BOOLEAN DEFAULT FALSE,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                    )
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(createGameSql)) {
            preparedStatement.execute();
        }
    }

    private static void executeBoardSchemaCreation(Connection connection) throws SQLException {
        String createBoardSql = """
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
        try (PreparedStatement preparedStatement = connection.prepareStatement(createBoardSql)) {
            preparedStatement.execute();
        }
    }
}

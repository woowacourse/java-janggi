package janggi.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initializeDatabase() {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
             Statement statement = connection.createStatement()) {

            statement.execute(CREATE_BOARD_TABLE);
            statement.execute(CREATE_BOARD_STATUS_TABLE);
        } catch (SQLException e) {
            throw new RuntimeException("데이터베이스 초기화 중 오류 발생", e);
        }
    }

    private static final String CREATE_BOARD_TABLE = """
            CREATE TABLE IF NOT EXISTS board (
                piece_id INTEGER PRIMARY KEY,
                x INTEGER NOT NULL,
                y INTEGER NOT NULL,
                piece TEXT NOT NULL,
                side TEXT NOT NULL
            );
            """;

    private static final String CREATE_BOARD_STATUS_TABLE = """
            CREATE TABLE IF NOT EXISTS board_status (
                status TEXT NOT NULL
            );
            """;

}

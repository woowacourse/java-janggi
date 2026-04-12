package janggi.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
    private static final String URL = "jdbc:h2:./janggi_db";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("DB 연결 실패: " + e.getMessage());
        }
    }

    public static void initializeDatabase() {
        String createGameTableQuery = """
                CREATE TABLE IF NOT EXISTS game (
                    game_id INT AUTO_INCREMENT PRIMARY KEY,
                    current_turn VARCHAR(10) NOT NULL
                );
                """;

        String createPieceTableQuery = """
                CREATE TABLE IF NOT EXISTS piece (
                    piece_id INT AUTO_INCREMENT PRIMARY KEY,
                    game_id INT,
                    team VARCHAR(10) NOT NULL,
                    piece_type VARCHAR(10) NOT NULL,
                    position_row INT NOT NULL,
                    position_column INT NOT NULL,
                    FOREIGN KEY (game_id) REFERENCES game(game_id) ON DELETE CASCADE
                );
                """;

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(createGameTableQuery);
            statement.execute(createPieceTableQuery);

        } catch (SQLException e) {
            throw new RuntimeException("DB 테이블 초기화 실패: " + e.getMessage());
        }
    }
}

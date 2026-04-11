package janggi.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnectionProvider {
    private static final String URL = "jdbc:h2:./janggi;MODE=MySQL";
    private static final String USER = "ethan";
    private static final String PASSWORD = "ethan";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("DB 연결에 실패했습니다.", e);
        }
    }

    public static void initDatabase() {
        String createGameTable = """
                CREATE TABLE IF NOT EXISTS game (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    turn VARCHAR(10) NOT NULL,
                    ongoing BOOLEAN NOT NULL,
                    state_type VARCHAR(20),
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
                """;

        String createPieceTable = """
                CREATE TABLE IF NOT EXISTS piece (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    game_id BIGINT NOT NULL,
                    camp VARCHAR(10) NOT NULL,
                    piece_type VARCHAR(20) NOT NULL,
                    row_pos INT NOT NULL,
                    col_pos INT NOT NULL,
                    FOREIGN KEY (game_id) REFERENCES game(id) ON DELETE CASCADE
                )
                """;

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(createGameTable);
            statement.execute(createPieceTable);
        } catch (SQLException e) {
            throw new RuntimeException("테이블 초기화 중 오류가 발생했습니다.", e);
        }
    }
}

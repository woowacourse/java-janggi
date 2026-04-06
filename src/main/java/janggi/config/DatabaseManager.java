package janggi.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    private static final String URL = "jdbc:h2:./janggi-db";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void initTable() {
        String gameTableSql = """
                CREATE TABLE IF NOT EXISTS janggi_game (
                    id BIGINT NOT NULL AUTO_INCREMENT,
                    turn VARCHAR(8) NOT NULL,
                
                    PRIMARY KEY (id)
                );
                """;

        String pieceTableSql = """
                CREATE TABLE IF NOT EXISTS piece (
                    id BIGINT NOT NULL AUTO_INCREMENT,
                    janggi_game_id BIGINT NOT NULL,
                    row_pos INT NOT NULL,
                    col_pos INT NOT NULL,
                    team VARCHAR(8) NOT NULL,
                    type VARCHAR(16) NOT NULL,
                
                    PRIMARY KEY (id),
                    CONSTRAINT fk_piece_game
                        FOREIGN KEY (janggi_game_id) REFERENCES janggi_game(id)
                        ON DELETE CASCADE
                );
                """;

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(gameTableSql);
            statement.execute(pieceTableSql);
        } catch (SQLException e) {
            throw new RuntimeException("DB 초기화 실패", e);
        }
    }

}

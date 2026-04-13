package janggi.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBInitializer {

    public static void initialize(Connection conn) throws SQLException {
        String createGameSql = """
                CREATE TABLE IF NOT EXISTS game (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    current_turn VARCHAR(20) NOT NULL,
                    winner VARCHAR(20) NOT NULL,
                    han_score DOUBLE NOT NULL,
                    cho_score DOUBLE NOT NULL,
                    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
                  );
                """;

        String createPieceSql = """
                CREATE TABLE IF NOT EXISTS piece (
                    game_id INT NOT NULL,
                    type VARCHAR(20) NOT NULL,
                    team VARCHAR(20) NOT NULL,
                    piece_row INT NOT NULL,
                    piece_col INT NOT NULL,
                    PRIMARY KEY (game_id, piece_row, piece_col),
                    FOREIGN KEY (game_id) REFERENCES game(id) ON DELETE CASCADE
                );
                """;

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createGameSql);
            stmt.execute(createPieceSql);
        }
    }
}

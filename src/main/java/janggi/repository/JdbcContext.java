package janggi.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcContext {

    private static final String DEFAULT_URL = "jdbc:sqlite:db/janggi.db";

    private final String url;

    public JdbcContext() {
        this(DEFAULT_URL);
    }

    public JdbcContext(String url) {
        this.url = url;
        initializeDatabase();
    }

    private void initializeDatabase() {
        String createGameTable = """
                CREATE TABLE IF NOT EXISTS game (
                    id          INTEGER PRIMARY KEY AUTOINCREMENT,
                    turn        TEXT    NOT NULL,
                    is_finished INTEGER NOT NULL DEFAULT 0,
                    winner      TEXT
                );
                """;

        String createPieceTable = """
                CREATE TABLE IF NOT EXISTS game_piece (
                    game_id    INTEGER NOT NULL,
                    piece_type TEXT    NOT NULL,
                    side       TEXT    NOT NULL,
                    x          INTEGER NOT NULL,
                    y          INTEGER NOT NULL,
                    FOREIGN KEY (game_id) REFERENCES game(id)
                );
                """;

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createGameTable);
            stmt.execute(createPieceTable);
        } catch (SQLException e) {
            throw new RuntimeException("DB 초기화 실패: " + e.getMessage());
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }
}

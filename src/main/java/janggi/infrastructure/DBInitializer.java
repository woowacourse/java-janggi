package janggi.infrastructure;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBInitializer {

    public static void initialize() {
        try (Connection connection = DBConnectionManager.getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(createGameTable());
            statement.execute(createPieceTable());

        } catch (SQLException e) {
            throw new RuntimeException("테이블 초기화에 실패했습니다.", e);
        }
    }

    private static String createGameTable() {
        return "CREATE TABLE IF NOT EXISTS game ("
                + "game_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "current_turn TEXT NOT NULL, "
                + "game_status TEXT NOT NULL, "
                + "winner TEXT, "
                + "created_at TEXT DEFAULT (datetime('now', 'localtime')), "
                + "updated_at TEXT DEFAULT (datetime('now', 'localtime'))"
                + ")";
    }

    private static String createPieceTable() {
        return "CREATE TABLE IF NOT EXISTS piece ("
                + "piece_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "game_id INTEGER NOT NULL, "
                + "row_pos INTEGER NOT NULL, "
                + "col_pos INTEGER NOT NULL, "
                + "piece_type TEXT NOT NULL, "
                + "team TEXT NOT NULL, "
                + "FOREIGN KEY (game_id) REFERENCES game(game_id)"
                + ")";
    }
}

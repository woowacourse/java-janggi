package janggi.manager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public final class DatabaseTestManager {

    private static final String SERVER = "localhost:3306";
    private static final String DATABASE = "chess_test";
    private static final DatabaseManager databaseManager = new DatabaseManager(SERVER, DATABASE);

    private DatabaseTestManager() {
    }

    public static DatabaseManager create() {
        return databaseManager;
    }

    public static void resetDatabase() throws SQLException {
        Connection connection = databaseManager.getConnection();

        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("DELETE FROM game_room");
            stmt.executeUpdate("DELETE FROM board");
        }
    }
}

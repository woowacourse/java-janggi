package janggi.manager;

public final class DatabaseTestManager {

    private static final String SERVER = "localhost:3306";
    private static final String DATABASE = "chess_test";
    private static final DatabaseManager databaseManager = new DatabaseManager(SERVER, DATABASE);

    private DatabaseTestManager() {
    }

    public static DatabaseManager create() {
        return databaseManager;
    }
}

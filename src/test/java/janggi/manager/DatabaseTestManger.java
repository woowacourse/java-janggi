package janggi.manager;

public final class DatabaseTestManger {

    private static final String SERVER = "localhost:3306";
    private static final String CHESS_TABLE = "chess_test";
    private static final DatabaseManager databaseManager = new DatabaseManager(SERVER, CHESS_TABLE);

    private DatabaseTestManger() {
    }

    public static DatabaseManager create() {
        return databaseManager;
    }
}

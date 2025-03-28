package janggi.config;

public final class DatabaseConfig {

    private static final String SERVER = "localhost:3306";
    private static final String DATABASE = "chess";

    private DatabaseConfig() {
    }

    public static String getServer() {
        return SERVER;
    }

    public static String getDatabase() {
        return DATABASE;
    }
}

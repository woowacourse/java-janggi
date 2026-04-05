package db;

public final class DbConfig {
    private static final String JDBC_URL = "jdbc:h2:./build/h2/janggi;AUTO_SERVER=TRUE";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    private DbConfig() {
    }

    public static String jdbcUrl() {
        return JDBC_URL;
    }

    public static String user() {
        return USER;
    }

    public static String password() {
        return PASSWORD;
    }
}


package db.util;

public final class TestDatabaseConstants {

    public static final String JDBC_URL = "jdbc:h2:mem:testdb;"
            + "MODE=MySQL;"
            + "DATABASE_TO_LOWER=TRUE;"
            + "DB_CLOSE_DELAY=-1";
    public static final String JDBC_USER = "sa";
    public static final String JDBC_PASSWORD = "";

    private TestDatabaseConstants() {}
}

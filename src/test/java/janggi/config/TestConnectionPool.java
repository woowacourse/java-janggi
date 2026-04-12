package janggi.config;

public class TestConnectionPool {
    public static ConnectionPool create() {
        return new ConnectionPool(
                "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
                "sa",
                "",
                10
        );
    }
}

package janggi.config;

import org.junit.jupiter.api.BeforeAll;

public class TestConfig {

    @BeforeAll
    public static void setUp() {
        System.setProperty("db.url", "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        System.setProperty("db.user", "sa");
        System.setProperty("db.password", "");
    }

}

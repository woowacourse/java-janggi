package config;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConfig {
    public static Connection createConnection() {
        try {
            return DriverManager.getConnection(
                    "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
                    "sa",
                    ""
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

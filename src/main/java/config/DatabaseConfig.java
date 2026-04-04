package config;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConfig {
    public static Connection createConnection() {
        try {
            return DriverManager.getConnection(
                    "jdbc:h2:file:./h2db/janggi;AUTO_SERVER=TRUE",
                    "sa",
                    ""
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

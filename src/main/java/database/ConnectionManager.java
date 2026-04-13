package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private final DatabaseConfig config;

    public ConnectionManager(DatabaseConfig config) {
        this.config = config;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                config.jdbcUrl(),
                config.user(),
                config.password()
        );
    }
}

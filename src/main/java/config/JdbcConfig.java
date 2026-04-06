package config;

import exception.InfraErrorMessage;
import exception.custom.DatabaseConnectionException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConfig {
    private static final JdbcConfig INSTANCE = new JdbcConfig();

    private static final String URL = ConfigLoader.load().getProperty("db.url");
    private static final String USER = ConfigLoader.load().getProperty("db.user");
    private static final String PASSWORD = ConfigLoader.load().getProperty("db.password");

    private JdbcConfig() {
    }

    public static JdbcConfig getInstance() {
        return INSTANCE;
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new DatabaseConnectionException(InfraErrorMessage.INVALID_DATABASE_INFORMATION.getMessage());
        }
    }
}

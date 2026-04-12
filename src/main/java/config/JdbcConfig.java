package config;

import exception.InfraErrorMessage;
import exception.custom.DatabaseConnectionException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcConfig {
    private static final JdbcConfig INSTANCE = new JdbcConfig();

    private final String url;
    private final String user;
    private final String password;

    private JdbcConfig() {
        Properties properties = ConfigLoader.load();

        this.url = properties.getProperty("db.url");
        this.user = properties.getProperty("db.user");
        this.password = properties.getProperty("db.password");
    }

    public static JdbcConfig getInstance() {
        return INSTANCE;
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new DatabaseConnectionException(InfraErrorMessage.INVALID_DATABASE_INFORMATION.getMessage());
        }
    }
}

package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    private static final String DEFAULT_URL = "jdbc:h2:~/janggi;AUTO_SERVER=TRUE";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    private DatabaseConfig(){}

    public static Connection getConnection() throws SQLException{
        String URL = System.getProperty("db.url", DEFAULT_URL);
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

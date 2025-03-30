package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionUtil {

    private static final String URL = PropertiesLoader.load().getProperty("db.url");
    private static final String USERNAME = PropertiesLoader.load().getProperty("db.username");
    private static final String PASSWORD = PropertiesLoader.load().getProperty("db.password");

    public static Connection createConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}

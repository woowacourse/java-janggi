package repository;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
    private static String URL;
    private static String USER;
    private static String PASSWORD;

    static {
        try (InputStream is = ConnectionManager.class.getClassLoader().getResourceAsStream("db.properties")) {
            Properties properties = new Properties();
            properties.load(is);

            URL = properties.getProperty("db.url");
            USER = properties.getProperty("db.user");
            PASSWORD = properties.getProperty("db.password");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ConnectionManager() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

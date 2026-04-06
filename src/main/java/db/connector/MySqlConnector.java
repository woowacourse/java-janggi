package db.connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlConnector {

    private static final String URL = "jdbc:mysql://localhost:3306/janggi";
    private static final String USERNAME = "janggi";
    private static final String PASSWORD = "janggi";

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException exception) {
            throw new IllegalStateException(exception);
        }
    }
}

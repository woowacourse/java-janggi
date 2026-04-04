package janggi.repository.h2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {
    private static final String URL = "jdbc:h2:~/janggi";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    private DataSource() {
        /* This utility class should not be instantiated */
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

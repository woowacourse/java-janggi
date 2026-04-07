package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static final String URL = "jdbc:h2:./data/janggidb;AUTO_SERVER=TRUE";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}

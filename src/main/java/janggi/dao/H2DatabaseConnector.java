package janggi.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.UUID;

public class H2DatabaseConnector implements DatabaseConnector{
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "";
    private final String dbName = "test_" + UUID.randomUUID();

    @Override
    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:h2:mem:" + dbName + ";MODE=MySQL;DB_CLOSE_DELAY=-1" , USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
    }
}

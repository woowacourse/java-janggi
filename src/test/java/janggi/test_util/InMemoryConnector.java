package janggi.test_util;

import janggi.infra.connector.DatabaseConnector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class InMemoryConnector implements DatabaseConnector {

    @Override
    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:h2:mem:janggi;DB_CLOSE_DELAY=-1", "sa", "");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

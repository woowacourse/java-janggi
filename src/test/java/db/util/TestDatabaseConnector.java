package db.util;

import db.connector.Connector;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestDatabaseConnector implements Connector {

    @Override
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    TestDatabaseConstants.JDBC_URL,
                    TestDatabaseConstants.JDBC_USER,
                    TestDatabaseConstants.JDBC_PASSWORD
            );
        } catch (SQLException exception) {
            throw new DataAccessException(exception);
        }
    }
}

package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteConnectionProvider implements ConnectionProvider {

    private static final String JDBC_URL = "jdbc:sqlite:janggi.db";

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL);
    }
}

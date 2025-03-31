package dao;

import static dao.DatabaseConfig.getDatabase;
import static dao.DatabaseConfig.getOption;
import static dao.DatabaseConfig.getPassword;
import static dao.DatabaseConfig.getServer;
import static dao.DatabaseConfig.getUsername;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {

    private static Connection connection;

    private ConnectionProvider() {
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(
                        "jdbc:mysql://"
                                + getServer()
                                + "/"
                                + getDatabase()
                                + getOption(), getUsername(), getPassword());
            } catch (final SQLException e) {
                throw new IllegalStateException("DB 커넥션 실패");
            }
        }
        return connection;
    }
}

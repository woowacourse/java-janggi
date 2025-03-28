package dao;

import static dao.DatabaseConfig.DATABASE;
import static dao.DatabaseConfig.OPTION;
import static dao.DatabaseConfig.PASSWORD;
import static dao.DatabaseConfig.SERVER;
import static dao.DatabaseConfig.USERNAME;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {

    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME,
                        PASSWORD);
            } catch (final SQLException e) {
                throw new RuntimeException("DB 커넥션 실패");
            }
        }
        return connection;
    }
}

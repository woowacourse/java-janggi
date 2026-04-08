package repository.connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlConnector implements Connector {

    private static final String CONNECTION_NOT_FOUND = "MySQL JDBC Driver를 찾을 수 없습니다.";

    private static final String URL = "jdbc:mysql://localhost:3306/JANGGI?serverTimezone=Asia/Seoul";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    @Override
    public Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(CONNECTION_NOT_FOUND);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

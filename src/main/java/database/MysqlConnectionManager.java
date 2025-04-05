package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlConnectionManager implements ConnectionManager{

    private static final String SERVER = "localhost:13306";
    private static final String DATABASE = "janggi";
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "user";
    private static final String PASSWORD = "password";

    private static final String URL = "jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION;

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.err.println("DB 연결 오류: " + e.getMessage());
            throw new RuntimeException("DB 연결 실패", e);
        }
    }
}

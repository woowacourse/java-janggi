package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:h2:./janggi;AUTO_SERVER=TRUE;INIT=RUNSCRIPT FROM './schema.sql'";

    private static final String USER = "sa";
    private static final String PASSWORD = "";

    private DBConnection() {
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("데이터베이스 연결에 실패했습니다: " + e.getMessage(), e);
        }
    }
}

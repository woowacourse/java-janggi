package janggi.domain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

    private static final String URL = "jdbc:mysql://localhost:3306/janggi";
    private static final String USER = "ever";
    private static final String PASSWORD = System.getenv("DB_PASSWORD");

    public static Connection getConnection() {
        try {
            if (PASSWORD == null) {
                throw new RuntimeException("환경 변수 'DB_PASSWORD'가 설정되지 않았습니다.");
            }
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("DB 연결에 실패했습니다.", e);
        }
    }
}

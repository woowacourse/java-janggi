package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/janggi_db";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("[ERROR] JDBC 드라이버를 찾을 수 없습니다.", e);
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] DB 연결에 실패했습니다.", e);
        }
    }
}

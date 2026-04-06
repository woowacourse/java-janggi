package database.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {

    private static final JanggiProperties properties = JanggiProperties.getInstance();

    private DBConnector() {
    }

    // TODO 커스텀 예외
    public static Connection getConnection() {
        try {
            String url = properties.getUrl();
            String username = properties.getUsername();
            String password = properties.getPassword();
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException("데이터베이스 연결 실패");
        }
    }

}

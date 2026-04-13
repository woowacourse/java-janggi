package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {
    private final String url;
    private final String username;
    private final String password;

    public ConnectionProvider(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException exception) {
            throw new IllegalStateException("[ERROR] 데이터베이스 연결에 실패했습니다.", exception);
        }
    }
}

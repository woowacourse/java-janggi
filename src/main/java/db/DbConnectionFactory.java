package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnectionFactory {
    private final String url;
    private final String user;
    private final String password;

    public DbConnectionFactory(ConfigLoader configLoader) {
        this.url = configLoader.getProperty("db.jdbc.url");
        this.user = configLoader.getProperty("db.jdbc.user");
        this.password = configLoader.getProperty("db.jdbc.password");

        if (this.url == null || this.user == null) {
            throw new IllegalStateException("데이터베이스 설정 정보가 누락되었습니다.");
        }
    }

    public Connection createConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
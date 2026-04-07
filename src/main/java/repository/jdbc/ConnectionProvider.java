package repository.jdbc;

import config.DbConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {

    private final DbConfig dbConfig;

    public ConnectionProvider(DbConfig dbConfig) {
        this.dbConfig = dbConfig;
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    dbConfig.url(),
                    dbConfig.username(),
                    dbConfig.password()
            );
        } catch (SQLException e) {
            throw new IllegalStateException("MySQL 연결에 실패했습니다.", e);
        }
    }
}

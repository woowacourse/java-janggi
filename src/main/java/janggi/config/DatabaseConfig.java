package janggi.config;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConfig {
    private static final Properties properties = new Properties();
    private static final ConnectionPool connectionPool;

    static {
        try (InputStream input = DatabaseConfig.class.getResourceAsStream("/application.properties")) {
            if (input == null) {
                throw new RuntimeException("application.properties 파일을 찾을 수 없습니다.");
            }
            properties.load(input);
        } catch (Exception e) {
            throw new RuntimeException("JDBC 초기화 실패", e);
        }

        connectionPool = new ConnectionPool(
                properties.getProperty("db.url"),
                properties.getProperty("db.id"),
                properties.getProperty("db.password"),
                5
        );
    }

    public static void shutdown() {
        connectionPool.shutdown();
    }

    public static PooledConnection getConnection() {
        return connectionPool.getPooledConnection();
    }

    public static ConnectionPool getPool() {
        return connectionPool;
    }
}

package db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
    private static final String PROPERTIES_FILE = "db.properties";
    private final String url;
    private final String user;
    private final String password;

    public ConnectionManager() {
        Properties properties = loadProperties();
        this.url = properties.getProperty("db.url");
        this.user = properties.getProperty("db.user");
        this.password = properties.getProperty("db.password");
        loadDriver(properties.getProperty("db.driver"));
    }

    private Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            if (input == null) {
                throw new IllegalStateException("db.properties 파일을 찾을 수 없습니다.");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new IllegalStateException("db.properties 로딩 실패", e);
        }
        return properties;
    }

    private void loadDriver(String driver) {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("DB 드라이버 로딩 실패: " + driver, e);
        }
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new IllegalStateException("DB 연결 실패", e);
        }
    }

    public <T> T inTransaction(TransactionCallback<T> callback) {
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try {
                T result = callback.execute(connection);
                connection.commit();
                return result;
            } catch (SQLException | RuntimeException e) {
                connection.rollback();
                throw new IllegalStateException("트랜잭션 실패", e);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("DB 연결 실패", e);
        }
    }
}

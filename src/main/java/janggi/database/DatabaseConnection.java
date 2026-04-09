package janggi.database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DatabaseConnection {
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;

    static {
        try (InputStream input = DatabaseConnection.class
                .getClassLoader()
                .getResourceAsStream("db.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            URL = properties.getProperty("db.url");
            USERNAME = properties.getProperty("db.username");
            PASSWORD = properties.getProperty("db.password");
        } catch (IOException e) {
            throw new IllegalStateException("DB 설정 파일을 읽을 수 없습니다.", e);
        }
    }

    public static void initSchema() {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            String sql = new String(DatabaseConnection.class
                    .getClassLoader()
                    .getResourceAsStream("schema.sql")
                    .readAllBytes());
            statement.execute(sql);
        } catch (IOException e) {
            throw new IllegalStateException("스키마 파일을 읽을 수 없습니다.", e);
        } catch (SQLException e) {
            throw new IllegalStateException("스키마 초기화에 실패했습니다.", e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new IllegalStateException("DB 연결에 실패했습니다.", e);
        }
    }
}

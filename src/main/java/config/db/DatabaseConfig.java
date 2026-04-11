package config.db;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.Properties;

public class DatabaseConfig {

    private static final Properties PROPERTIES = new Properties();
    private static final String PROPERTIES_FILE_NAME = "db.properties";

    static {
        try (InputStream in = DatabaseConfig.class
                .getClassLoader()
                .getResourceAsStream(PROPERTIES_FILE_NAME)) {
            PROPERTIES.load(in);
        } catch (IOException e) {
            throw new IllegalStateException("데이터베이스 연결 불가", e);
        }
    }

    public static void initSchema() {
        try (InputStream in = DatabaseConfig.class
                .getClassLoader()
                .getResourceAsStream("schema.sql");
             Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            String sql = new String(Objects.requireNonNull(in).readAllBytes(), StandardCharsets.UTF_8);
            stmt.execute(sql);
        } catch (IOException | SQLException e) {
            throw new IllegalStateException("스키마 초기화 실패", e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    PROPERTIES.getProperty("db.url"),
                    PROPERTIES.getProperty("db.user"),
                    PROPERTIES.getProperty("db.password")
            );
        } catch (SQLException e) {
            throw new IllegalStateException("데이터베이스 연결 불가", e);
        }
    }
}

package config.db;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
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
            throw new RuntimeException("데이터베이스 로딩 실패");
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                PROPERTIES.getProperty("db.url"),
                PROPERTIES.getProperty("db.user"),
                PROPERTIES.getProperty("db.password")
        );
    }

    public static void initSchema() {
        try (InputStream in = DatabaseConfig.class
                .getClassLoader()
                .getResourceAsStream("schema.sql");
             Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            String sql = new String(in.readAllBytes(), StandardCharsets.UTF_8);
            stmt.execute(sql);
        } catch (IOException | SQLException e) {
            throw new RuntimeException("스키마 초기화 실패", e);
        }
    }
}

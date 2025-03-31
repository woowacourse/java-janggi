package dao;

import java.io.IOException;
import java.util.Properties;

public class DatabaseConfig {

    private static final Properties properties = new Properties();

    static {
        try {
            properties.load(DatabaseConfig.class.getResourceAsStream("/database.properties"));
        } catch (IOException e) {
            throw new IllegalStateException("데이터베이스 설정 파일을 불러오는데 실패했습니다.", e);
        }
    }

    public static String getServer() {
        return properties.getProperty("server", "localhost:13306");
    }

    public static String getDatabase() {
        return properties.getProperty("database", "janggi");
    }

    public static String getOption() {
        return properties.getProperty("option", "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC");
    }

    public static String getUsername() {
        return properties.getProperty("username", "root");
    }

    public static String getPassword() {
        return properties.getProperty("password", "root");
    }
}

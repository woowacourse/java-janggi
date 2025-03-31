package config;

import infra.dao.exception.DatabaseException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class DBConfig {

    private static final Properties properties = new Properties();

    static {
        try (final InputStream input =
                 DBConfig.class.getClassLoader().getResourceAsStream("db.properties")) {

            if (input == null) {
                throw new IllegalStateException("db.properties 파일을 찾을 수 없습니다.");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new DatabaseException("DB 설정 파일 로딩 중 오류 발생", e);
        }
    }

    private DBConfig() {
    }

    public static String getUrl() {
        return properties.getProperty("db.url");
    }

    public static String getUsername() {
        return properties.getProperty("db.username");
    }

    public static String getPassword() {
        return properties.getProperty("db.password");
    }
}

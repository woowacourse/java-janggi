package config;

import infra.dao.exception.DatabaseException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class DBConfig {

    public static final String DB_URL_PROPERTY = "db.url";
    public static final String DB_USERNAME_PROPERTY = "db.username";
    public static final String DB_PASSWORD_PROPERTY = "db.password";
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
        final String url = properties.getProperty(DB_URL_PROPERTY);
        if (url == null) {
            throw new IllegalStateException("url 속성이 설정되지 않았습니다.");
        }

        return url;
    }

    public static String getUsername() {
        final String username = properties.getProperty(DB_USERNAME_PROPERTY);
        if (username == null) {
            throw new IllegalStateException("username 속성이 설정되지 않았습니다.");
        }

        return username;
    }

    public static String getPassword() {
        final String password = properties.getProperty(DB_PASSWORD_PROPERTY);
        if (password == null) {
            throw new IllegalStateException("password 속성이 설정되지 않았습니다.");
        }

        return password;
    }
}

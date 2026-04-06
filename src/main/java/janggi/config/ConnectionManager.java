package janggi.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static final String INVALID_URL_MESSAGE = "DB URL은 비어 있을 수 없습니다.";
    private static final String INVALID_USER_MESSAGE = "DB USER는 비어 있을 수 없습니다.";
    private static final String INVALID_PASSWORD_MESSAGE = "DB PASSWORD는 null일 수 없습니다.";

    private final String url;
    private final String user;
    private final String password;

    public ConnectionManager(String url, String user, String password) {
        validateUrl(url);
        validateUser(user);
        validatePassword(password);
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    private void validateUrl(String url) {
        if (url == null || url.isBlank()) {
            throw new IllegalArgumentException(INVALID_URL_MESSAGE);
        }
    }

    private void validateUser(String user) {
        if (user == null || user.isBlank()) {
            throw new IllegalArgumentException(INVALID_USER_MESSAGE);
        }
    }

    private void validatePassword(String password) {
        if (password == null) {
            throw new IllegalArgumentException(INVALID_PASSWORD_MESSAGE);
        }
    }
}

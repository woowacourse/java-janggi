package infra.jdbc;

import infra.jdbc.exception.JdbcRepositoryException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcConnectionManager {
    private static final String URL_KEY = "db.url";
    private static final String USERNAME_KEY = "db.username";
    private static final String PASSWORD_KEY = "db.password";
    private static final String DEFAULT_PROPERTIES_PATH = "jdbc.properties";

    private final String url;
    private final String user;
    private final String password;

    public JdbcConnectionManager(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public static JdbcConnectionManager defaultConnectionManager() {
        return fromClasspathProperties(DEFAULT_PROPERTIES_PATH);
    }

    public static JdbcConnectionManager fromClasspathProperties(String propertiesPath) {
        Properties properties = loadProperties(propertiesPath);
        return new JdbcConnectionManager(
                properties.getProperty(URL_KEY),
                properties.getProperty(USERNAME_KEY),
                properties.getProperty(PASSWORD_KEY)
        );
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    private static Properties loadProperties(String propertiesPath) {
        try (InputStream inputStream = JdbcConnectionManager.class.getClassLoader()
                .getResourceAsStream(propertiesPath)) {
            if (inputStream == null) {
                throw new JdbcRepositoryException("JDBC 설정 파일을 찾을 수 없습니다: " + propertiesPath);
            }

            Properties properties = new Properties();
            properties.load(inputStream);
            return properties;
        } catch (IOException e) {
            throw new JdbcRepositoryException("JDBC 설정 파일을 읽는 데 실패했습니다: " + propertiesPath, e);
        }
    }
}

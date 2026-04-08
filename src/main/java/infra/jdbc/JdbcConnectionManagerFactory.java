package infra.jdbc;

import infra.jdbc.exception.JdbcRepositoryException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class JdbcConnectionManagerFactory {
    private static final String URL_KEY = "db.url";
    private static final String USERNAME_KEY = "db.username";
    private static final String PASSWORD_KEY = "db.password";
    private static final String DEFAULT_PROPERTIES_PATH = "jdbc.properties";

    private JdbcConnectionManagerFactory() {
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

    private static Properties loadProperties(String propertiesPath) {
        try (InputStream inputStream = JdbcConnectionManagerFactory.class.getClassLoader()
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

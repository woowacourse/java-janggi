package repository.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import repository.RepositoryErrorMessage;

public class JdbcConnectionGenerator {

    private static final String DB_URL_KEY = "db.url";
    private static final String DB_USERNAME_KEY = "db.username";
    private static final String DB_PASSWORD_KEY = "db.password";


    private final String URL;
    private final String NAME;
    private final String PASSWORD;

    private JdbcConnectionGenerator(String url, String name, String password) {
        URL = url;
        NAME = name;
        PASSWORD = password;
    }

    public static JdbcConnectionGenerator create(String configFileName) {
        Properties properties = new Properties();

        ClassLoader classLoader = JdbcConnectionGenerator.class.getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(configFileName)) {
            if (inputStream == null) {
                throw new RuntimeException(RepositoryErrorMessage.CONFIG_FILE_NOT_FOUND.getMessage());
            }

            properties.load(inputStream);

            String dbUrl = properties.getProperty(DB_URL_KEY);
            String dbUsername = properties.getProperty(DB_USERNAME_KEY);
            String dbPassword = properties.getProperty(DB_PASSWORD_KEY);

            return new JdbcConnectionGenerator(dbUrl, dbUsername, dbPassword);

        } catch (IOException e) {
            throw new IllegalStateException(RepositoryErrorMessage.ERROR_ON_CONFIG_FILE_READING.getMessage());
        }
    }

    public Connection getDBConnection() {
        try {
            return DriverManager.getConnection(URL, NAME, PASSWORD);
        } catch (SQLException e) {
            throw new IllegalStateException(RepositoryErrorMessage.FAIL_TO_GET_CONNECTION.getMessage());
        }
    }
}

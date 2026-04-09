package database.connection;

import database.exception.DataAccessException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static database.exception.DataAccessError.PROPERTIES_FILE_NOT_FOUND;
import static database.exception.DataAccessError.PROPERTIES_LOAD_FAILED;

public class JanggiProperties {

    private static final JanggiProperties INSTANCE = new JanggiProperties();

    private String url;
    private String username;
    private String password;

    private JanggiProperties() {
        loadProperties();
    }

    public static JanggiProperties getInstance() {
        return INSTANCE;
    }

    private void loadProperties() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if (inputStream == null) {
                throw new DataAccessException(PROPERTIES_FILE_NOT_FOUND.getMessage());
            }

            Properties properties = new Properties();
            properties.load(inputStream);

            this.url = properties.getProperty("database.url");
            this.username = properties.getProperty("database.username");
            this.password = properties.getProperty("database.password");

        } catch (IOException e) {
            throw new DataAccessException(PROPERTIES_LOAD_FAILED.getMessage());
        }
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}

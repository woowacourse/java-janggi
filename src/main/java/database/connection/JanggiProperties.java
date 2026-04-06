package database.connection;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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

    // TOOD 커스텀 예외.
    private void loadProperties() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if (inputStream == null) {
                throw new RuntimeException("application.properties 파일을 찾을 수 없습니다.");
            }

            Properties properties = new Properties();
            properties.load(inputStream);

            this.url = properties.getProperty("database.url");
            this.username = properties.getProperty("database.username");
            this.password = properties.getProperty("database.password");

        } catch (IOException e) {
            throw new RuntimeException(e);
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

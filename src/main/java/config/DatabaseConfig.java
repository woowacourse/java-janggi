package config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class DatabaseConfig {
    private DatabaseConfig() {
    }

    public static ConnectionFactory createConnectionFactory() {
        Properties props = loadProperties("jdbc.properties");
        String url = props.getProperty("jdbc.url");
        String user = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password", "");
        return () -> openConnection(url, user, password);
    }

    private static Connection openConnection(String url, String user, String password) throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    private static Properties loadProperties(String name) {
        Properties props = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try (InputStream in = loader.getResourceAsStream(name)) {
            loadProperties(name, in, props);
        } catch (IOException e) {
            throw new IllegalStateException("설정 로드 실패: " + name, e);
        }
        return props;
    }

    private static void loadProperties(String name, InputStream in, Properties props) throws IOException {
        if (in == null) {
            throw new IllegalStateException("classpath에서 찾을 수 없음: " + name);
        }
        props.load(in);
    }
}

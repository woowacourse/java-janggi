package janggi.repository;

import janggi.exception.InfrastructureException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static final String PROPERTIES_PATH = "db.properties";
    private static final Properties PROPERTIES = new Properties();

    static {
        loadProperties();
    }

    private static void loadProperties() {
        try (InputStream input = getInputStream()) {
            validateInputStream(input);
            PROPERTIES.load(input);
        } catch (IOException e) {
            throw new InfrastructureException("설정 파일을 읽는 중 에러가 발생했습니다.", e);
        }
    }

    private static InputStream getInputStream() {
        return DBConnection.class.getClassLoader().getResourceAsStream(PROPERTIES_PATH);
    }

    private static void validateInputStream(InputStream input) {
        if (input == null) {
            throw new InfrastructureException(PROPERTIES_PATH + " 파일을 찾을 수 없습니다.");
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                PROPERTIES.getProperty("db.url"),
                PROPERTIES.getProperty("db.user"),
                PROPERTIES.getProperty("db.password")
        );
    }
}

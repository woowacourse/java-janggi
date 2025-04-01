package janggi.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String PROPERTIES_FILE = "db.properties";
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConnectionUtil.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            if (input == null) {
                throw new IllegalStateException("파일을 찾을 수 없는 오류 발생: " + PROPERTIES_FILE);
            }
            properties.load(input);
        } catch (IOException e) {
            throw new IllegalStateException("파일 읽는 중 오류 발생: " + PROPERTIES_FILE, e);
        }
    }

    public static Connection getConnection() {
        String server = properties.getProperty("server");
        String database = properties.getProperty("database");
        String option = properties.getProperty("option");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");

        String url = "jdbc:mysql://" + server + "/" + database + option;

        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new IllegalStateException("DB 연결 오류:" + e.getMessage());
        }
    }
}

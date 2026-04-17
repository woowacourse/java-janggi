package db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DbConnection {

    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;

    static {
        try (InputStream is = DbConnection.class
                .getClassLoader()
                .getResourceAsStream("db.properties")) {

            Properties props = new Properties();
            props.load(is);

            URL = props.getProperty("db.url");
            USERNAME = props.getProperty("db.username");
            PASSWORD = props.getProperty("db.password");

            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            throw new RuntimeException("DB 설정 로드 실패", e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (Exception e) {
            throw new RuntimeException("DB 연결 실패", e);
        }
    }
}

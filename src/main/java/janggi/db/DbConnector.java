package janggi.db;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DbConnector {

    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;

    static {
        Properties properties = new Properties();
        try (InputStream inputStream = DbConnector.class.getClassLoader().getResourceAsStream("db.properties")) {
            validateResource(inputStream, "db.properties");
            properties.load(inputStream);

            URL = properties.getProperty("db.url");
            USER = properties.getProperty("db.username");
            PASSWORD = properties.getProperty("db.password");
        } catch (IOException e) {
            throw new RuntimeException("[ERROR] 설정 파일을 읽는 중 오류가 발생했습니다.", e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] DB 연결에 실패했습니다. 서버가 켜져 있는지 확인해 주세요.", e);
        }
    }

    public static void initDatabase() {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             InputStream inputStream = DbConnector.class.getClassLoader().getResourceAsStream("schema.sql")) {
            validateResource(inputStream, "schema.sql");
            String sql = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            statement.execute(sql);
            System.out.println("[INFO] 데이터베이스 테이블이 초기화되었습니다.");
        } catch (Exception e) {
            throw new RuntimeException("[ERROR] DB 초기화 중 오류가 발생했습니다.", e);
        }
    }

    private static void validateResource(InputStream inputStream, String fileName) {
        if (inputStream == null) {
            throw new RuntimeException(String.format("[ERROR] %s 파일을 찾을 수 없습니다.", fileName));
        }
    }
}

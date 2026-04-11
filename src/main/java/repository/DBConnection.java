package repository;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private static final String CONFIG_FILE = "db.properties";

    public static Connection getConnection() {
        Properties properties = new Properties();

        try (InputStream input = DBConnection.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                throw new RuntimeException("[ERROR] " + CONFIG_FILE + " 파일을 찾을 수 없습니다.");
            }
            properties.load(input);

            return DriverManager.getConnection(
                    properties.getProperty("db.url"),
                    properties.getProperty("db.user"),
                    properties.getProperty("db.password")
            );
        } catch (IOException | SQLException e) {
            throw new RuntimeException("[ERROR] DB 연결 실패: " + e.getMessage());
        }
    }
}

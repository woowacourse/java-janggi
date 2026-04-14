package janggi.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class DBConnection {
    private static final String URL = "jdbc:h2:./janggi_db";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("DB 연결 실패: " + e.getMessage());
        }
    }

    public static void initializeDatabase() {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            String sql = new String(
                    Objects.requireNonNull(DBConnection.class.getResourceAsStream("/schema.sql")).readAllBytes()
            );
            statement.execute(sql.split(";")[0]);
            statement.execute(sql.split(";")[1]);

        } catch (SQLException | IOException e) {
            throw new RuntimeException("DB 테이블 초기화 실패: " + e.getMessage());
        }
    }
}

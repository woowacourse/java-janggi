package repository;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;

public class DBConnectionUtil {

    private static final String URL = System.getProperty("db.url", "jdbc:h2:~/janggi;AUTO_SERVER=TRUE");
    private static final String USERNAME = System.getProperty("db.username", "sa");
    private static final String PASSWORD = System.getProperty("db.password", "");

    public static void initializeSchema() {
        try (Connection con = getConnection();
             Statement stmt = con.createStatement();
             InputStream inputStream = DBConnectionUtil.class.getClassLoader().getResourceAsStream("schema.sql")) {

            if (inputStream == null) {
                throw new IllegalStateException("[ERROR] schema.sql 파일을 찾을 수 없습니다.");
            }

            String sql = new BufferedReader(new InputStreamReader(inputStream))
                    .lines()
                    .collect(Collectors.joining("\n"));

            String[] tokens = sql.split(";");
            for (String token : tokens) {
                if (!token.trim().isEmpty()) {
                    stmt.execute(token.trim());
                }
            }
        } catch (Exception e) {
            throw new IllegalStateException("[ERROR] 스키마 초기화 실패: " + e.getMessage());
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}

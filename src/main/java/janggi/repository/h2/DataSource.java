package janggi.repository.h2;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;

public class DataSource {
    private static final String DIR = "game.sql";
    private static final String URL = "jdbc:h2:~/janggi";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    static {
        try {
            Class.forName("org.h2.Driver");
            DataSource.init();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private DataSource() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void init() {
        try (Connection conn = DataSource.getConnection();
             Statement stmt = conn.createStatement()) {

            InputStream resourceAsStream = readFile();

            String sql = new BufferedReader(new InputStreamReader(resourceAsStream))
                    .lines()
                    .collect(Collectors.joining("\n"));

            stmt.executeUpdate(sql);

        } catch (Exception e) {
            throw new IllegalStateException("DB 초기화 실패", e);
        }
    }

    private static InputStream readFile() {
        InputStream resourceAsStream = DataSource.class
                .getClassLoader()
                .getResourceAsStream(DIR);

        if (resourceAsStream == null) {
            throw new IllegalStateException("파일 찾기 실패~");
        }
        return resourceAsStream;
    }
}

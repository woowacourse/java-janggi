package database.jdbc;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;


public class DatabaseInitializer {

    private static final String SCHEMA_PATH = "Schema.sql";

    public static void initialize() {
        String sql = loadSchema();
        try (Connection conn = DatabaseConnector.getConnection();
             Statement statement = conn.createStatement()) {

            for (String query : sql.split(";")) {
                String string = query.trim();
                if (!string.isEmpty()) {
                    statement.execute(string);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("데이터베이스 초기화에 실패했습니다. 경로: " + SCHEMA_PATH, e);
        }
    }

    private static String loadSchema() {
        InputStream inputStream = DatabaseInitializer.class.getClassLoader()
                .getResourceAsStream(DatabaseInitializer.SCHEMA_PATH);
        if (inputStream == null) {
            throw new RuntimeException(DatabaseInitializer.SCHEMA_PATH + " 파일을 찾을 수 없습니다.");
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (Exception e) {
            throw new RuntimeException(DatabaseInitializer.SCHEMA_PATH + " 읽기에 실패했습니다.", e);
        }
    }
}

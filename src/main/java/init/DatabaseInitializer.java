package init;

import config.H2ConnectionManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.stream.Collectors;

public class DatabaseInitializer {

    public static void init(H2ConnectionManager connectionManager) {
        try (Connection connection = connectionManager.getConnection();
             Statement stmt = connection.createStatement()) {

            String sql = loadSql();

            for (String query : sql.split(";")) {
                String trimmed = query.trim();
                if (!trimmed.isEmpty()) {
                    stmt.execute(trimmed);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("DB 초기화 실패", e);
        }
    }

    private static String loadSql() {
        InputStream is = DatabaseInitializer.class
                .getClassLoader()
                .getResourceAsStream("schema.sql");

        if (is == null) {
            throw new IllegalArgumentException("SQL 파일을 찾을 수 없음: " + "schema.sql");
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
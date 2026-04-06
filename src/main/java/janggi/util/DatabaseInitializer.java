package janggi.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.stream.Collectors;

public class DatabaseInitializer {

    public static void initialize(Connection conn) {
        String sql = readSqlFile();

        String[] queries = sql.split(";");

        try (Statement stmt = conn.createStatement()) {
            for (String query : queries) {
                if (!query.trim().isEmpty()) {
                    stmt.execute(query);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("쿼리 실행 중 에러 발생!", e);
        }
    }

    private static String readSqlFile() {
        try (InputStream is = DatabaseInitializer.class.getResourceAsStream("/schema.sql");
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

            return reader.lines().collect(Collectors.joining("\n"));

        } catch (Exception e) {
            throw new RuntimeException("schema.sql 파일을 찾을 수 없거나 읽을 수 없습니다.", e);
        }
    }
}

package janggi.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Statement;

public class TestDBInitializer {

    public static void initSchema(ConnectionPool pool) {
        String sql = readSqlFile("/schema.sql");

        try (PooledConnection pooled = pool.getPooledConnection();
             Statement stmt = pooled.getConnection().createStatement()) {
            for (String query : sql.split(";")) {
                String trimmed = query.trim();
                if (!trimmed.isEmpty()) {
                    stmt.execute(trimmed);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("테스트 스키마 초기화 실패", e);
        }
    }

    private static String readSqlFile(String path) {
        try (InputStream is = TestDBInitializer.class.getResourceAsStream(path)) {
            if (is == null) {
                throw new RuntimeException("SQL 파일을 찾을 수 없습니다: " + path);
            }
            return new String(is.readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException("SQL 파일 읽기 실패", e);
        }
    }

    public static void clearAll(ConnectionPool pool) {
        try (PooledConnection pooled = pool.getPooledConnection();
             Statement stmt = pooled.getConnection().createStatement()) {
            stmt.execute("DELETE FROM piece");
            stmt.execute("DELETE FROM game");
        } catch (SQLException e) {
            throw new RuntimeException("테스트 데이터 정리 실패", e);
        }
    }
}

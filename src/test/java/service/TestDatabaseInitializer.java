package service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.stream.Collectors;

public class TestDatabaseInitializer {

    private final Connection connectionManager;

    public TestDatabaseInitializer(Connection connectionManager) {
        this.connectionManager = connectionManager;
    }

    public void init() {
        try (Statement stmt = connectionManager.createStatement()) {

            connectionManager.setAutoCommit(false);

            runSql(stmt, "schema.sql");
            runSql(stmt, "data.sql");
            connectionManager.commit();

        } catch (Exception e) {
            throw new RuntimeException("DB 초기화 실패", e);
        }
    }

    private void runSql(Statement stmt, String fileName) throws Exception {
        String sql = loadSql(fileName);

        for (String query : sql.split(";")) {
            String trimmed = query.trim();
            if (!trimmed.isEmpty()) {
                stmt.execute(trimmed);
            }
        }
    }

    private String loadSql(String fileName) {
        InputStream is = getClass()
                .getClassLoader()
                .getResourceAsStream(fileName);

        if (is == null) {
            throw new IllegalArgumentException("[ERROR] SQL 파일 없음: " + fileName);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

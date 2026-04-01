package service;

import config.H2ConnectionManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.stream.Collectors;

public class TestDatabaseInitializer {

    private static final String SCHEMA_SQL = "schema.sql";
    private static final String DATA_SQL = "data.sql";
    private final H2ConnectionManager connectionManager;

    public TestDatabaseInitializer(H2ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public void init() {
        try (Connection conn = connectionManager.getConnection();
             Statement stmt = conn.createStatement()) {

            conn.setAutoCommit(false);

            runSql(stmt, SCHEMA_SQL);
            runSql(stmt, DATA_SQL);

            conn.commit();

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

package janggi.db;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public final class DatabaseInitializer {

    private static final String SCHEMA_PATH = "/schema.sql";
    private static final String SCHEMA_LOAD_FAILED = "[ERROR] DB 스키마를 읽을 수 없습니다.";
    private static final String SCHEMA_INIT_FAILED = "[ERROR] DB 스키마를 초기화할 수 없습니다.";

    public void initialize() {
        try (Connection connection = H2ConnectionManager.getConnection()) {
            String schema = loadSchema();
            for (String statement : schema.split(";")) {
                String sql = statement.trim();
                if (sql.isEmpty()) {
                    continue;
                }
                try (Statement jdbcStatement = connection.createStatement()) {
                    jdbcStatement.execute(sql);
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException(SCHEMA_INIT_FAILED, e);
        }
    }

    private String loadSchema() {
        try (InputStream inputStream = DatabaseInitializer.class.getResourceAsStream(SCHEMA_PATH)) {
            if (inputStream == null) {
                throw new IllegalStateException(SCHEMA_LOAD_FAILED);
            }
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException(SCHEMA_LOAD_FAILED, e);
        }
    }
}

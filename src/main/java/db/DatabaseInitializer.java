package db;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {
    private static final String SCHEMA_FILE = "schema.sql";
    private final ConnectionManager connectionManager;

    public DatabaseInitializer(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public void initialize() {
        String schema = loadSchema();
        executeSchema(schema);
    }

    private String loadSchema() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(SCHEMA_FILE)) {
            if (input == null) {
                throw new IllegalStateException("schema.sql 파일을 찾을 수 없습니다.");
            }
            return new String(input.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException("schema.sql 로딩 실패", e);
        }
    }

    private void executeSchema(String schema) {
        try (Connection connection = connectionManager.getConnection();
             Statement statement = connection.createStatement()) {
            for (String sql : schema.split(";")) {
                String trimmed = sql.trim();
                if (!trimmed.isEmpty()) {
                    statement.execute(trimmed);
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("스키마 실행 실패", e);
        }
    }
}

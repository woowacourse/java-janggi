package db.jdbc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    private static final String SCHEMA_PATH = "/schema.sql";

    private final ConnectionManager connectionManager;

    public DatabaseInitializer(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public void initialize() {
        String schemaSql = readSchemaSql();

        try (Connection connection = connectionManager.getConnection();
             Statement statement = connection.createStatement()) {

            for (String sql : schemaSql.split(";")) {
                String trimmedSql = sql.trim();
                if (trimmedSql.isEmpty()) {
                    continue;
                }
                statement.execute(trimmedSql);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("데이터베이스 초기화에 실패했습니다.", e);
        }
    }

    private String readSchemaSql() {
        InputStream inputStream = getClass().getResourceAsStream(SCHEMA_PATH);
        if (inputStream == null) {
            throw new IllegalStateException("schema.sql 파일을 찾을 수 없습니다.");
        }

        try (BufferedReader reader = new BufferedReader(
            new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return reader.lines()
                .reduce("", (acc, line) -> acc + line + "\n");
        } catch (IOException e) {
            throw new IllegalStateException("schema.sql 파일을 읽는 데 실패했습니다.", e);
        }
    }
}

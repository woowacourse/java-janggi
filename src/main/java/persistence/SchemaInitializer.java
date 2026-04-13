package persistence;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SchemaInitializer {
    private final ConnectionFactory connectionFactory;

    public SchemaInitializer(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void initialize() {
        String schemaSql = readSchemaSql();

        try (Connection connection = connectionFactory.getConnection();
             Statement statement = connection.createStatement()) {
            for (String sql : schemaSql.split(";")) {
                String trimmed = sql.trim();
                if (!trimmed.isEmpty()) {
                    statement.execute(trimmed);
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("스키마 초기화 실패", e);
        }
    }

    private String readSchemaSql() {
        try (InputStream inputStream = SchemaInitializer.class.getClassLoader().getResourceAsStream("schema.sql")) {
            if (inputStream == null) {
                throw new IllegalStateException("schema.sql 파일을 찾을 수 없습니다.");
            }
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException("schema.sql 읽기 실패", e);
        }
    }
}

package util;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.Statement;

public class SchemaInitializer {
    public static void initialize(DataSource dataSource) {
        try {
            String schema = Files.readString(Path.of("schema.sql"), StandardCharsets.UTF_8);

            try (Connection connection = dataSource.getConnection();
                 Statement statement = connection.createStatement()) {
                for (String sql : schema.split(";")) {
                    String trimmedSql = sql.trim();
                    if (trimmedSql.isEmpty()) {
                        continue;
                    }
                    statement.execute(trimmedSql);
                }
            }
        } catch (Exception e) {
            throw new IllegalStateException("스키마 초기화에 실패했습니다.", e);
        }
    }
}

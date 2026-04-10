package janggi.db;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initialize(final DatabaseConfig config) {
        try (Connection connection = DriverManager.getConnection(
                config.getConnectionUrl(), config.getUsername(), config.getPassword());
             Statement statement = connection.createStatement()) {

            String sql = readSchemaSql();
            for (String query : sql.split(";")) {
                String trimmed = query.trim();
                if (!trimmed.isEmpty()) {
                    statement.execute(trimmed);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("데이터베이스 초기화에 실패했습니다.", e);
        }
    }

    private static String readSchemaSql() {
        try (InputStream input = DatabaseInitializer.class
                .getClassLoader()
                .getResourceAsStream("schema.sql")) {
            return new String(input.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("schema.sql 파일을 읽을 수 없습니다.", e);
        }
    }
}

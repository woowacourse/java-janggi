package janggi.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    private static final String JDBC_URL = "jdbc:sqlite:janggi.db";
    private static final String SCHEMA_FILE_PATH = "schema.sql";

    public void initialize() {
        try (
            Connection connection = DriverManager.getConnection(JDBC_URL);
            Statement statement = connection.createStatement()
        ) {
            executeSchema(statement, readSchema());
        } catch (SQLException | IOException e) {
            throw new IllegalStateException("데이터베이스 초기화에 실패했습니다.", e);
        }
    }

    private String readSchema() throws IOException {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(SCHEMA_FILE_PATH)) {
            if (inputStream == null) {
                throw new IllegalStateException("schema.sql 파일을 찾을 수 없습니다.");
            }
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    private void executeSchema(Statement statement, String schema) throws SQLException {
        for (String query : schema.split(";")) {
            String trimmedQuery = query.trim();
            if (!trimmedQuery.isEmpty()) {
                statement.executeUpdate(trimmedQuery);
            }
        }
    }
}

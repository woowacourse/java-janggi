package janggi.repository.util;

import janggi.exception.DatabaseException;
import janggi.repository.util.connection.ConnectionProvider;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Statement;

public class SchemaInitializer {

    private final ConnectionProvider connectionProvider;

    private static final String SCHEMA_FILE_PATH = "src/main/resources/schema.sql";

    public SchemaInitializer(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public void init() {
        try (Connection connection = connectionProvider.getConnection();
             Statement statement = connection.createStatement()) {
            String sql = Files.readString(Paths.get(SCHEMA_FILE_PATH));

            String[] queries = sql.split(";");

            for (String query : queries) {
                if (!query.trim().isEmpty()) {
                    statement.execute(query);
                }
            }

        } catch (Exception e) {
            throw new DatabaseException("데이터베이스 스키마 초기화 실패", e);
        }
    }
}

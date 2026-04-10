package database;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {
    private static final String SCHEMA_RESOURCE_PATH = "/schema.sql";

    private final ConnectionManager connectionManager;

    public DatabaseInitializer(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public void initialize() {
        try (Connection connection = connectionManager.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(loadSchema());
        } catch (SQLException | IOException exception) {
            throw new IllegalStateException("데이터베이스 초기화에 실패했습니다.", exception);
        }
    }

    private String loadSchema() throws IOException {
        try (InputStream inputStream = getClass().getResourceAsStream(SCHEMA_RESOURCE_PATH)) {
            if (inputStream == null) {
                throw new IllegalStateException("스키마 파일을 찾을 수 없습니다.");
            }
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}

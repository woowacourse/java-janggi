package janggi.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    private final JdbcConnectionManager connectionManager;

    public DatabaseInitializer(JdbcConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public void init() {
        try (
                Connection connection = connectionManager.getConnection();
                Statement statement = connection.createStatement()
        ) {
            statement.execute(readSchema());
        } catch (SQLException | IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private String readSchema() throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(schemaStream(), StandardCharsets.UTF_8));
        return reader.lines()
                .reduce("", (first, second) -> first + second + "\n");
    }

    private InputStream schemaStream() {
        InputStream stream = getClass().getResourceAsStream("/schema.sql");
        if (stream == null) {
            throw new IllegalArgumentException("schema.sql 파일을 찾을 수 없습니다.");
        }
        return stream;
    }
}

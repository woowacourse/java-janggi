package repository.jdbc;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SchemaInitializer {

    private static final String SCHEMA_FILE = "/janggi_game_initialize_schema.sql";
    private static final String SCHEMA_FILE_NOT_FOUND = "스키마 파일을 찾을 수 없습니다.";
    private static final String SCHEMA_FILE_READ_FAILED = "스키마 파일을 읽는 데 실패했습니다.";
    private static final String SCHEMA_INITIALIZE_FAILED = "스키마 초기화에 실패했습니다.";
    private static final String SCHEMA_EXECUTE_FAILED = "스키마 실행에 실패했습니다.";

    private final DataSource dataSource;

    public SchemaInitializer(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void initialize() {
        final String schema = readSchema();

        try (Connection connection = dataSource.getConnection()) {
            executeSchema(connection, schema);
        } catch (final SQLException exception) {
            throw new RuntimeException(SCHEMA_INITIALIZE_FAILED, exception);
        }
    }


    private String readSchema() {
        final InputStream inputStream = getClass().getResourceAsStream(SCHEMA_FILE);

        if (inputStream == null) {
            throw new RuntimeException(SCHEMA_FILE_NOT_FOUND);
        }

        return read(inputStream);
    }

    private String read(final InputStream inputStream) {
        try (InputStream stream = inputStream) {
            return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (final IOException exception) {
            throw new RuntimeException(SCHEMA_FILE_READ_FAILED, exception);
        }
    }

    private void executeSchema(final Connection connection, final String schema) {
        try (Statement statement = connection.createStatement()) {
            executeStatements(statement, schema);
        } catch (final SQLException exception) {
            throw new RuntimeException(SCHEMA_EXECUTE_FAILED, exception);
        }
    }

    private void executeStatements(final Statement statement, final String schema) throws SQLException {
        for (final String sql : schema.split(";")) {
            execute(statement, sql.trim());
        }
    }

    private void execute(final Statement statement, final String sql) throws SQLException {
        if (sql.isEmpty()) {
            return;
        }

        statement.execute(sql);
    }
}

package persistence.datasource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import javax.sql.DataSource;

public class DataSourceInitializer {

    private static final String SCHEMA_RESOURCE_PATH = "/schema.sql";

    private final DataSource dataSource;

    public DataSourceInitializer(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void initialize() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            List<String> statements = statements();
            executeStatements(statements, statement);
        } catch (Exception exception) {
            throw new IllegalStateException(exception);
        }
    }

    private List<String> statements() throws IOException {
        return Arrays.stream(readSchema().split(";"))
                .map(String::trim)
                .filter(statement -> !statement.isBlank())
                .toList();
    }

    private String readSchema() throws IOException {
        try (InputStream inputStream = DataSourceInitializer.class.getResourceAsStream(SCHEMA_RESOURCE_PATH)) {
            validateResource(inputStream);
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    private void validateResource(InputStream inputStream) {
        if (inputStream == null) {
            throw new IllegalStateException("schema.sql 리소스를 찾을 수 없습니다.");
        }
    }

    private void executeStatements(List<String> statements, Statement statement) throws SQLException {
        for (String each : statements) {
            statement.addBatch(each);
        }
        statement.executeBatch();
    }
}

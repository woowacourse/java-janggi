package db.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import org.h2.tools.RunScript;

public final class TestSchema {

    private static final String SCHEMA_FILE_PATH = "/schema.sql";

    public void execute(Connection connection) {
        try (InputStream schemaInput = TestSchema.class.getResourceAsStream(SCHEMA_FILE_PATH)) {
            validateResourceExist(schemaInput);
            executeSchema(schemaInput, connection);
        } catch (SQLException | IOException exception) {
            throw new DataAccessException(exception);
        }
    }

    private void validateResourceExist(InputStream schemaInput) {
        if (schemaInput == null) {
            throw new IllegalStateException(SCHEMA_FILE_PATH + " 파일이 존재하지 않습니다.");
        }
    }

    private void executeSchema(
            InputStream schemaInput,
            Connection connection
    ) throws IOException, SQLException {
        try (InputStreamReader reader = new InputStreamReader(schemaInput, StandardCharsets.UTF_8)) {
            RunScript.execute(connection, reader);
        }
    }
}

package database;

import database.connection.DBConnector;
import database.exception.DataAccessException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static database.exception.DatabaseError.CONNECTION_FAILED;

public class SchemaInitializer {

    public SchemaInitializer() {
    }

    public void readShemaSQLFile() {
        try (InputStream inputStream = SchemaInitializer.class.getClassLoader().getResourceAsStream("schema_v1.sql")) {
            if (inputStream == null) {
                throw new IllegalArgumentException("schema_v1.sql 파일을 찾을 수 없습니다.");
            }

            String schema = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            executeSchemaQuery(schema);
        } catch (IOException e) {
            throw new DataAccessException(CONNECTION_FAILED.getMessage(), e);
        }
    }

    public void executeSchemaQuery(String schema) {
        try (Connection connection = DBConnector.getConnection()) {
            for (String ddl : schema.split(";")) {
                if (ddl.trim().isEmpty()) {
                    continue;
                }

                try (Statement statement = connection.createStatement()) {
                    statement.execute(ddl);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(CONNECTION_FAILED.getMessage(), e);
        }
    }

}

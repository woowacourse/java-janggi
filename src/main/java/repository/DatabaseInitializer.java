package repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import support.DataAccessException;

public final class DatabaseInitializer {

    private static final String QUERY_DELIMITER = ";";

    private final DataSource dataSource;

    public DatabaseInitializer(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void init(Path schemaPath) {
        try {
            String content = Files.readString(schemaPath);
            executeQueries(content.split(QUERY_DELIMITER));
        } catch (IOException e) {
            throw new DataAccessException("DDL 파일을 읽을 수 없습니다.", e);
        }
    }

    private void executeQueries(String[] queries) {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            for (String query : queries) {
                if (!query.isBlank()) {
                    stmt.addBatch(query.trim());
                }
            }
            stmt.executeBatch();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }
}

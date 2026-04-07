package janggi.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    private static final String SCHEMA_SCRIPT = "RUNSCRIPT FROM 'classpath:/schema.sql' CHARSET 'UTF-8'";
    private static final String SCHEMA_INIT_FAILED = "[ERROR] DB 스키마를 초기화할 수 없습니다.";

    private final ConnectionManager connectionManager;

    public DatabaseInitializer(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public void initialize() {
        try (
                Connection connection = connectionManager.getConnection();
                Statement jdbcStatement = connection.createStatement()
        ) {
            jdbcStatement.execute(SCHEMA_SCRIPT);
        } catch (SQLException e) {
            throw new IllegalStateException(SCHEMA_INIT_FAILED, e);
        }
    }
}

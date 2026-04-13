package data;

import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.UUID;

abstract class DatabaseTestHelper {

    protected DataSource createDataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:" + UUID.randomUUID() + ";DB_CLOSE_DELAY=-1");
        dataSource.setUser("sa");
        dataSource.setPassword("");
        initializeSchema(dataSource);
        return dataSource;
    }

    protected Connection getConnection(DataSource dataSource) throws SQLException {
        return dataSource.getConnection();
    }

    private void initializeSchema(DataSource dataSource) {
        String schema = readSchema();

        try (
                Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement()
        ) {
            for (String query : schema.split(";")) {
                String trimmed = query.trim();
                if (!trimmed.isEmpty()) {
                    statement.execute(trimmed);
                }
            }
        } catch (SQLException exception) {
            throw new IllegalStateException("테스트 스키마 초기화에 실패했습니다.", exception);
        }
    }

    private String readSchema() {
        try {
            return new String(Objects.requireNonNull(
                    getClass().getResourceAsStream("/create_tables.sql")).readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException exception) {
            throw new IllegalStateException("테스트 스키마를 읽을 수 없습니다.", exception);
        }
    }
}

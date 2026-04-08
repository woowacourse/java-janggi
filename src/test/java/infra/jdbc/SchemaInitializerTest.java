package infra.jdbc;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SchemaInitializerTest {
    private JdbcConnectionManager connectionManager;
    private SchemaInitializer schemaInitializer;

    @BeforeEach
    void setUp() {
        connectionManager = JdbcTestSupport.connectionManager();
        schemaInitializer = new SchemaInitializer(connectionManager);
    }

    @AfterEach
    void clearAll() {
        JdbcTestSupport.clearAll(connectionManager);
    }

    @Test
    void 초기화하면_janggi_game과_game_piece_테이블을_생성한다() throws Exception {
        // when
        schemaInitializer.initialize();

        // then
        try (Connection connection = connectionManager.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();

            assertThat(hasTable(metaData, "JANGGI_GAME")).isTrue();
            assertThat(hasTable(metaData, "GAME_PIECE")).isTrue();
        }
    }

    private boolean hasTable(DatabaseMetaData metaData, String tableName) throws Exception {
        try (ResultSet resultSet = metaData.getTables(null, null, tableName, null)) {
            return resultSet.next();
        }
    }
}

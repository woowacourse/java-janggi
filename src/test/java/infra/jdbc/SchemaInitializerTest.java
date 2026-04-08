package infra.jdbc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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

    @Test
    void 초기화하면_game_piece는_허용되지_않은_piece_type을_저장할_수_없다() throws Exception {
        // given
        schemaInitializer.initialize();

        try (Connection connection = connectionManager.getConnection()) {
            long gameId = insertGame(connection);

            // when & then
            assertThatThrownBy(() -> insertInvalidPieceType(connection, gameId))
                    .isInstanceOf(SQLException.class)
                    .hasMessageContaining("CHK_GAME_PIECE_TYPE");
        }
    }

    private boolean hasTable(DatabaseMetaData metaData, String tableName) throws Exception {
        try (ResultSet resultSet = metaData.getTables(null, null, tableName, null)) {
            return resultSet.next();
        }
    }

    private long insertGame(Connection connection) throws SQLException {
        String sql = """
                INSERT INTO janggi_game (current_turn, status, winner, created_at, updated_at)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, "CHO");
            statement.setString(2, "RUNNING");
            statement.setString(3, null);
            statement.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            statement.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                generatedKeys.next();
                return generatedKeys.getLong(1);
            }
        }
    }

    private void insertInvalidPieceType(Connection connection, long gameId) throws SQLException {
        String sql = """
                INSERT INTO game_piece (game_id, row_index, column_index, side, piece_type)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, gameId);
            statement.setInt(2, 0);
            statement.setInt(3, 0);
            statement.setString(4, "CHO");
            statement.setString(5, "INVALID");
            statement.executeUpdate();
        }
    }
}

package repository.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JdbcTemplateTest {

    private static final String CONFIG_FILE_NAME = "database.properties";

    private static final JdbcConnectionGenerator CONNECTION_GENERATOR = JdbcConnectionGenerator.create(
            CONFIG_FILE_NAME);
    private static final Connection DB_CONNECTION = CONNECTION_GENERATOR.getDBConnection();

    private final JdbcTemplate template = new JdbcTemplate();
    private static final String CREATE_TEST_PIECE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS piece (" +
            "piece_id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
            "piece_row INT NOT NULL, " +
            "piece_col INT NOT NULL, " +
            "team VARCHAR(10) NOT NULL, " +
            "type VARCHAR(10) NOT NULL)";

    @AfterEach
    void clearAll() throws SQLException {
        String sql = "DROP ALL OBJECTS";
        template.executeCommand(DB_CONNECTION, sql);
    }

    @Test
    @DisplayName("read을 잘 수행한다")
    void executeRead_good() {
        //given
        String testSql = "SELECT *";

        Assertions.assertDoesNotThrow(
                () -> template.executeRead(DB_CONNECTION, testSql, (resultSet) -> null)
        );
    }

    @Test
    @DisplayName("command 잘 수행한다")
    void executeCommand_good() {

        Assertions.assertDoesNotThrow(
                () -> template.executeCommand(DB_CONNECTION, CREATE_TEST_PIECE_TABLE_SQL)
        );
    }

    @Test
    @DisplayName("batch 잘 수행한다")
    void executeBatch_good() throws SQLException {
        int expectedResultSize = 3;
        template.executeCommand(DB_CONNECTION, CREATE_TEST_PIECE_TABLE_SQL);

        String sql = "INSERT INTO piece(piece_row, piece_col, team, type) VALUES (?, ?, ?, ?)";
        List<List<Object>> tempPieces = new ArrayList<>();
        tempPieces.add(List.of(1, 1, "CHO", "CHA"));
        tempPieces.add(List.of(2, 1, "HAN", "JOL"));
        tempPieces.add(List.of(3, 2, "CHO", "JANG"));

        template.executeBatchCommand(DB_CONNECTION, sql, tempPieces);

        int count = template.executeRead(DB_CONNECTION, "SELECT COUNT(*) AS cnt FROM piece", rs -> rs.getInt("cnt"))
                .get(0);
        Assertions.assertEquals(expectedResultSize, count);
    }
}

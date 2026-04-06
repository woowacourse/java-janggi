package repository.jdbc;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Connection;
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
    private static final String CREATE_TEST_TABLE_SQL = "CREATE TABLE IF NOT EXISTS test_table (" +
            "test_id BIGINT PRIMARY KEY, " +
            "test_int_data INT NOT NULL, " +
            "test_string_data VARCHAR(10) NOT NULL" +
            ")";

    @AfterEach
    void clearAll() {
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
    @DisplayName("테이블 생성 명령을 잘 수행한다")
    void executeTableCreateCommand_good() {

        Assertions.assertDoesNotThrow(
                () -> template.executeCommand(DB_CONNECTION, CREATE_TEST_TABLE_SQL)
        );
    }

    @Test
    @DisplayName("저장을 잘 한다")
    void executeSave() {
        //given
        template.executeCommand(DB_CONNECTION, CREATE_TEST_TABLE_SQL);
        String testSql = "INSERT INTO test_table(test_id, test_int_data, test_string_data) VALUES(?, ?, ?)";

        //when
        Object result = template.executeSave(DB_CONNECTION, testSql, 1, 1, "good");

        //then
        assertNotNull(result);
    }

    @Test
    @DisplayName("batch 저장 명령을 잘 수행한다")
    void executeBatchSave_good() {
        int expectedResultSize = 3;
        template.executeCommand(DB_CONNECTION, CREATE_TEST_TABLE_SQL);

        String testSql = "INSERT INTO test_table(test_id, test_int_data, test_string_data) VALUES(?, ?, ?)";
        List<List<Object>> tempPieces = new ArrayList<>();
        tempPieces.add(List.of(1, 1, "gd"));
        tempPieces.add(List.of(2, 1000, "god"));
        tempPieces.add(List.of(3, 200000, "good"));

        template.executeBatchSave(DB_CONNECTION, testSql, tempPieces);

        int count = template.executeRead(
                        DB_CONNECTION,
                        "SELECT COUNT(*) AS cnt FROM test_table",
                        resultSet -> resultSet.getInt("cnt")
                )
                .getFirst();
        Assertions.assertEquals(expectedResultSize, count);
    }

    @Test
    @DisplayName("업데이트 명령을 잘 수행한다")
    void executeCommand() {
        int expectedResultSize = 1;
        template.executeCommand(DB_CONNECTION, CREATE_TEST_TABLE_SQL);

        String testSql = "INSERT INTO test_table(test_id, test_int_data, test_string_data) VALUES(?, ?, ?)";

        int rowID = 3;
        template.executeSave(DB_CONNECTION, testSql, rowID, 200000, "good");

        int newTesIntData = 10;
        String newTestStringData = "gd";
        int count = template.executeCommand(
                DB_CONNECTION,
                "UPDATE test_table SET test_int_data = ?, test_string_data = ? WHERE test_id = ?",
                newTesIntData,
                newTestStringData,
                rowID
        );
        Assertions.assertEquals(expectedResultSize, count);

    }

    @Test
    @DisplayName("Batch 업데이트를 잘 한다")
    void executeBatchCommand() {
        int expectedResultSize = 2;
        template.executeCommand(DB_CONNECTION, CREATE_TEST_TABLE_SQL);

        String testSql = "INSERT INTO test_table(test_id, test_int_data, test_string_data) VALUES(?, ?, ?)";
        List<List<Object>> tempPieces = new ArrayList<>();
        tempPieces.add(List.of(1, 1, "gd"));
        tempPieces.add(List.of(2, 1, "god"));
        tempPieces.add(List.of(3, 2, "good"));

        template.executeBatchSave(DB_CONNECTION, testSql, tempPieces);

        int newTesIntData = 10;
        String newTestStringData = "gd";
        int count = template.executeCommand(
                DB_CONNECTION,
                "UPDATE test_table SET test_int_data = ?, test_string_data = ? WHERE test_int_data = ?",
                newTesIntData,
                newTestStringData,
                1
        );
        Assertions.assertEquals(expectedResultSize, count);
    }
}

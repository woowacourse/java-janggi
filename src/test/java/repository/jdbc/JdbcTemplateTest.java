package repository.jdbc;

import java.sql.SQLException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JdbcTemplateTest {

    private static final String CONFIG_FILE_NAME = "database.properties";

    private JdbcConnectionGenerator generator;
    private JdbcTemplate template;

    @BeforeEach
    void setUp() {
        generator = JdbcConnectionGenerator.create(CONFIG_FILE_NAME);
        template = new JdbcTemplate(generator);
    }

    @AfterEach
    void clearAll() throws SQLException {
        String sql = "DROP TABLE piece";
        template.command(sql);
    }

    @Test
    @DisplayName("read을 잘 수행한다")
    void read_good() {
        //given
        String testSql = "SELECT *";

        Assertions.assertDoesNotThrow(
                () -> template.read(testSql)
        );
    }

    @Test
    @DisplayName("command 잘 수행한다")
    void command_good() {
        String sql = "CREATE TABLE IF NOT EXISTS piece (" +
                "piece_id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                "piece_row INT NOT NULL, " +
                "piece_col INT NOT NULL, " +
                "team VARCHAR(10) NOT NULL, " +
                "type VARCHAR(10) NOT NULL)";

        Assertions.assertDoesNotThrow(
                () -> template.command(sql)
        );
    }
}

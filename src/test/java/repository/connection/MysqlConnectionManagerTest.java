package repository.connection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

class MysqlConnectionManagerTest {

    private final MysqlConnectionManager manager = new MysqlConnectionManager();

    @Test
    void connection() throws SQLException {
        try (final var connection = manager.getConnection()) {
            Assertions.assertThat(connection).isNotEqualTo(null);
        }
    }
}

package janggi.dao.connection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;

class MysqlConnectionTest {
    private final MysqlConnection databaseConnection = new MysqlConnection();

    @Test
    public void connection() {
        try (final var connection = databaseConnection.getConnection()) {
            Assertions.assertThat(connection).isNotNull();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

package database.connector;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;
import org.junit.jupiter.api.Test;

class MySQLConnectorTest {

    private final MySQLConnector mySQLConnector = new MySQLConnector();

    @Test
    public void connection() throws SQLException {
        try (final var connection = mySQLConnector.getConnection()) {
            assertThat(connection).isNotNull();
        }
    }
}

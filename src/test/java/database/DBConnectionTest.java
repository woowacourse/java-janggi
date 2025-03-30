package database;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;
import org.junit.jupiter.api.Test;

public class DBConnectionTest {
    private final DbConnection dbConnection = DbConnection.getInstance();

    @Test
    public void connection() throws SQLException {
        try (final var connection = dbConnection.getConnection()) {
            assertThat(connection).isNotNull();
        }
    }
}

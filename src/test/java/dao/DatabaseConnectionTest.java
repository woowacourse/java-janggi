package dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;
import org.junit.jupiter.api.Test;

class DatabaseConnectionTest {

    private final DatabaseConnection databaseConnection = new DatabaseConnection();

    @Test
    public void connection() {
        try (final var connection = databaseConnection.getConnection()) {
            assertThat(connection).isNotNull();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

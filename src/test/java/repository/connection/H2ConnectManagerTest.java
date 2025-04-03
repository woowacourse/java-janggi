package repository.connection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class H2ConnectManagerTest {

    private final H2ConnectManager manager = new H2ConnectManager();

    @Test
    void connection() throws SQLException {
        try (final var connection = manager.getConnection()) {
            Assertions.assertThat(connection).isNotEqualTo(null);
        }
    }
}

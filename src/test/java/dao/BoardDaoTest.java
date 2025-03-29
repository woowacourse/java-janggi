package dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;
import org.junit.jupiter.api.Test;

class BoardDaoTest {
    private final BoardDao boardDao = new BoardDao();

    @Test
    void connection() throws SQLException {
        try (final var connection = Connector.getConnection()) {
            assertThat(connection).isNotNull();
        }
    }
}
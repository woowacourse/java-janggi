package db;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;
import org.junit.jupiter.api.Test;

class JanggiDaoTest {
    private final JanggiDao janggiDao = new JanggiDao();

    @Test
    public void connection() throws SQLException {
        try (final var connection = janggiDao.getConnection()) {
            assertThat(connection).isNotNull();
        }
    }
}

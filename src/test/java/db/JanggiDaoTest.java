package db;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;
import org.junit.jupiter.api.Test;

class JanggiDaoTest {
    private final JanggiDao userJanggiDao = new JanggiDao();

    @Test
    public void connection() throws SQLException {
        try (final var connection = userJanggiDao.getConnection()) {
            assertThat(connection).isNotNull();
        }
    }
}

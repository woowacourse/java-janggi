package janggi.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;
import org.junit.jupiter.api.Test;

class JanggiJdbcDaoTest {

    private final JanggiJdbcDao janggiJdbcDao = new JanggiJdbcDao();

    @Test
    public void connection() throws SQLException {
        try (final var connection = janggiJdbcDao.getConnection()) {
            assertThat(connection).isNotNull();
        }
    }

}

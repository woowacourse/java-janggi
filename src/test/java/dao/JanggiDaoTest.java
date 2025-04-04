package dao;

import static dao.ConnectionUtil.getConnection;
import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;

class JanggiDaoTest {
    @Test
    public void connection() {
        try (final var connection = getConnection()) {
            assertThat(connection).isNotNull();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

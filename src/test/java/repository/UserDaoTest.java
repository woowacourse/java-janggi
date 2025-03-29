package repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;
import org.junit.jupiter.api.Test;

class UserDaoTest {

    private final UserDao userDao = new UserDao();

    @Test
    public void connection() {
        try (final var connection = userDao.getConnection()) {
            assertThat(connection).isNotNull();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

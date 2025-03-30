package janggi.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;
import org.junit.jupiter.api.Test;

class UserDaoTest {

    private final UserDao userDao = new UserDao();

    @Test
    public void connection() throws SQLException {
        try (final var connection = userDao.getConnection()) {
            assertThat(connection).isNotNull();
        }
    }

    @Test
    public void addUser() {
        final var user = new User("testUserId", "testUser");
        userDao.addUser(user);
    }

    @Test
    public void findByUserId() {
        final var user = userDao.findByUserId("testUserId");

        assertThat(user).isEqualTo(new User("testUserId", "testUser"));
    }

    @Test
    public void updateUsername() {
        final var user = userDao.updateUsername("testUserId", "mint");

        assertThat(user).isEqualTo(new User("testUserId", "mint"));
    }

    @Test
    public void deleteUser() {
        final var user = userDao.deleteUser("testUserId");

        assertThat(user).isEqualTo(new User("testUserId", "mint"));
    }

}


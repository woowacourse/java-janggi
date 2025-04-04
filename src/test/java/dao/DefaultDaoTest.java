package dao;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class DefaultDaoTest {
    private final DefaultDao defaultDao = new DefaultDao();

    @Test
    public void connection() {
        final var connection = defaultDao.getConnection();
        assertThat(connection).isNotNull();
    }
}

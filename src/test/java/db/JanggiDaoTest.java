package db;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JanggiDaoTest {
    private JanggiDao janggiDao;

    @BeforeEach
    void setUp() {
        // 인메모리 H2 데이터베이스 설정 (MySQL 모드로 동작시키려면 MODE=MySQL 옵션 추가)
        final JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;MODE=MySQL");
        dataSource.setUser("sa");
        dataSource.setPassword("");

        janggiDao = new JanggiDao(dataSource);
    }

    @Test
    public void connection() throws SQLException {
        try (final var connection = janggiDao.getConnection()) {
            assertThat(connection).isNotNull();
        }
    }
}

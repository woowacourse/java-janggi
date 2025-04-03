package dao;

import java.sql.SQLException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class TestDatabaseConnectionManagerTest {

    @Test
    void 테스트_데이터베이스_연결을_성공한다() {
        ConnectionManager manager = new TestDatabaseConnectionManager();

        try (final var connection = manager.getConnection()) {
            Assertions.assertThat(connection).isNotNull();
        } catch (SQLException e) {
            e.getStackTrace();
            throw new RuntimeException(e);
        }
    }
}

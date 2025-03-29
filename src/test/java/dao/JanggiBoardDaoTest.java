package dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.assertj.core.api.Assertions.assertThat;

class JanggiBoardDaoTest {
    private final JanggiBoardDao dao = new JanggiBoardDao();

    @DisplayName("드라이버 연결 테스트")
    @Test
    void driverConnection() {
        // given

        // when
        Connection connection = dao.getConnection();

        // then
        assertThat(connection).isNotNull();
    }
}

package janggi.database;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JanggiDaoTest {

    @Test
    @DisplayName("Mysql DB에 연결할 수 없다면 테스트가 실패한다")
    void getConnection() {
        // given
        assertThatCode(() -> {
            final JanggiDao janggiDao = new JanggiDao();
            janggiDao.getConnection();
        }).doesNotThrowAnyException();
    }
}

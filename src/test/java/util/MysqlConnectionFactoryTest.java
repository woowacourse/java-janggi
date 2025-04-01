package util;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MysqlConnectionFactoryTest {

    @Test
    @DisplayName("커넥션 획득에 성공하면 예외가 발생하지 않는다")
    void getConnectionTest() {
        // when & then
        assertThatCode(() -> new MysqlConnectionFactory().getConnection())
                .doesNotThrowAnyException();
    }
}

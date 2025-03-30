package janggi.db;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ConnectionTest {
    @DisplayName("오류 없이 connection이 생성되는지 확인한다.")
    @Test
    void connectionTest() {
        assertThatCode(Connection::new).doesNotThrowAnyException();
    }
}

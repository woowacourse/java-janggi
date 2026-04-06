package janggi.util;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;

class DatabaseInitTest {
    @Test
    void DB_연결이_성공한다() {
        assertThatCode(() -> DatabaseInit.initialize(DBConnectionManager.getConnection()))
                .doesNotThrowAnyException();
    }
}



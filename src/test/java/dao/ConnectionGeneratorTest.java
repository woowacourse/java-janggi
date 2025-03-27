package dao;

import fixture.DatabaseConnectionFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ConnectionGeneratorTest {

    @Test
    void 데이터베이스_네트워크_연결_테스트() {
        Assertions.assertThatCode(DatabaseConnectionFixture::getTestConnection)
                .doesNotThrowAnyException();
    }
}

package dao;

import dao.init.ConnectionGenerator;
import fixture.TestMySQLConnectionGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ConnectionGeneratorTest {

    private final ConnectionGenerator connectionGenerator = new TestMySQLConnectionGenerator();

    @Test
    void MySQL_데이터베이스_네트워크_연결_테스트() {
        Assertions.assertThatCode(connectionGenerator::createConnection)
                .doesNotThrowAnyException();
    }
}

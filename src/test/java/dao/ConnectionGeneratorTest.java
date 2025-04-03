package dao;

import dao.init.ConnectionGenerator;
import dao.init.MySQLConnectionGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ConnectionGeneratorTest {

    private final ConnectionGenerator connectionGenerator = new MySQLConnectionGenerator();

    @Test
    void 프로덕션_MySQL_서버_네트워크_연결_테스트() {
        Assertions.assertThatCode(connectionGenerator::createConnection)
                .doesNotThrowAnyException();
    }
}

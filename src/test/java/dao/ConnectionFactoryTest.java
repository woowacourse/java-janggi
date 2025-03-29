package dao;

import dao.init.ConnectionFactory;
import fixture.TestMySQLConnectionFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ConnectionFactoryTest {

    private final ConnectionFactory connectionFactory = new TestMySQLConnectionFactory();

    @Test
    void 데이터베이스_네트워크_연결_테스트() {
        Assertions.assertThatCode(connectionFactory::createConnection)
                .doesNotThrowAnyException();
    }
}

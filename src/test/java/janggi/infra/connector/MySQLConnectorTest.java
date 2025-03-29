package janggi.infra.connector;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class MySQLConnectorTest {

    @Test
    @Disabled
    void 커넥션을_얻는다() {
        // given
        final MySQLConnector connector = new MySQLConnector();

        // expected
        assertThatCode(connector::getConnection)
                .doesNotThrowAnyException();
    }
}

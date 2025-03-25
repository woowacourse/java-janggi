package janggi.infra.connector;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class InMemoryConnectorTest {

    @Test
    void 커넥션을_얻는다() {
        // given
        final InMemoryConnector inMemoryConnector = new InMemoryConnector();

        // expected
        assertThatCode(inMemoryConnector::getConnection)
                .doesNotThrowAnyException();
    }
}

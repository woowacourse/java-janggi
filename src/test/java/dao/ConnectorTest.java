package dao;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.Test;

class ConnectorTest {
    @Test
    void connectionTest() {
        assertThatCode(() -> {
            try (final var connection = Connector.getConnection()) {
            }
        }).doesNotThrowAnyException();
    }
}
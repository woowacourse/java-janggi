package dao;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.Test;

class BoardDaoTest {
    @Test
    void connection() {
        assertThatCode(() -> {
            try (final var connection = Connector.getConnection()) {
            }
        }).doesNotThrowAnyException();
    }
}
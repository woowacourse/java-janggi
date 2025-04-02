package janggi.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatNoException;

class ConnectionManagerTest {

    private ConnectionManager connectionManager;

    @BeforeEach
    void setUp() {
        connectionManager = new ConnectionManager();
    }

    @Test
    void 연결이_성공했다면_예외가_발생하지_않는다() {
        assertThatNoException()
                .isThrownBy(() -> connectionManager.getConnection());
    }
}

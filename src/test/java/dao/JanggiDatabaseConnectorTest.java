package dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.assertj.core.api.Assertions.assertThat;

class JanggiDatabaseConnectorTest {
    @DisplayName("드라이버 연결 테스트")
    @Test
    void driverConnection() {
        // given
        DatabaseConnector connector = new JanggiDatabaseConnector();

        // when
        Connection connection = connector.getConnection();

        // then
        assertThat(connection).isNotNull();
    }
}

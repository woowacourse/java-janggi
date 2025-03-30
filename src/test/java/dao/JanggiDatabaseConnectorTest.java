package dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

class JanggiDatabaseConnectorTest {
    @DisplayName("드라이버 연결 테스트")
    @Test
    void driverConnection() {
        // given
        DatabaseConnector connector = new JanggiDatabaseConnector();

        // when
        try (Connection connection = connector.getConnection()) {
            // then
            assertThat(connection).isNotNull();
        } catch (SQLException e) {
            fail("데이터베이스 연결 실패");
        }
    }
}

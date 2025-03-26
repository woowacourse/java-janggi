package dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ConnectionProviderTest {

    @Nested
    class ValidCases {

        @DisplayName("장기 데이터베이스 연결 테스트")
        @Test
        public void connection() {
            try (final Connection connection = ConnectionProvider.getConnection()) {
                assertThat(connection).isNotNull();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

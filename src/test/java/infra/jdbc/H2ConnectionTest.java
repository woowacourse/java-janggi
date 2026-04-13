package infra.jdbc;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class H2ConnectionTest {
    private JdbcConnectionManager connectionManager;

    @BeforeEach
    void setUp() {
        connectionManager = JdbcTestSupport.connectionManager();
    }

    @AfterEach
    void clearAll() {
        JdbcTestSupport.clearAll(connectionManager);
    }

    @Test
    void h2에_jdbc로_연결할_수_있다() throws Exception {
        // given
        String sql = "SELECT 1";

        // when
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            // then
            assertThat(connection.isClosed()).isFalse();
            assertThat(resultSet.next()).isTrue();
            assertThat(resultSet.getInt(1)).isEqualTo(1);
        }
    }
}

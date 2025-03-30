package dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public final class MySQLConnectorTest {
    private final Connector connector = new MySQLConnector();

    @Test
    @DisplayName("MySQL DB 연결을 확인한다.")
    void test_getConnection() throws SQLException {
        try (final Connection connection = connector.getConnection()) {
            assertThat(connection).isNotNull();
        }
    }
}

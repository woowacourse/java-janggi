package janggi.database;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MySQLDatabaseConnectionTest {

    private final MySQLDatabaseConnection mySQLDatabaseConnection = MySQLDatabaseConnection.getInstance();

    @DisplayName("커넥션 테스트")
    @Test
    void connectionTest() throws SQLException {

        // given

        // when & then
        try (final Connection connection = mySQLDatabaseConnection.getConnection()) {
            assertThat(connection).isNotNull();
            assertThat(connection.isClosed()).isFalse();
            assertThat(connection.isValid(1)).isTrue();
        }
    }
}

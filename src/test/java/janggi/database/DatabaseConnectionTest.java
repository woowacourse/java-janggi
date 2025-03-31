package janggi.database;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.database.dao.DatabaseConnection;
import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DatabaseConnectionTest {

    private final DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

    @BeforeAll
    static void setUpClass() {
        DatabaseConnection.setTestMode(true);
    }

    @AfterAll
    static void tearDownClass() {
        DatabaseConnection.setTestMode(false);
    }

    @DisplayName("커넥션 테스트")
    @Test
    void connectionTest() throws SQLException {

        // given

        // when & then
        try (final Connection connection = databaseConnection.getConnection()) {
            assertThat(connection).isNotNull();
            assertThat(connection.isClosed()).isFalse();
            assertThat(connection.isValid(1)).isTrue();
        }
    }

    @DisplayName("커넥션 닫기 테스트")
    @Test
    void connectionCloseTest() throws SQLException {

        // given
        final Connection connection = databaseConnection.getConnection();

        // when
        databaseConnection.closeConnection(connection);

        // then
        assertThat(connection.isClosed()).isTrue();
    }
}

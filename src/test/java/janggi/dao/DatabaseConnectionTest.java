package janggi.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DatabaseConnectionTest {

    private final DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

    @DisplayName("커넥션 테스트")
    @Test
    public void connectionTest() throws SQLException {

        // given

        // when & then
        try (final var connection = databaseConnection.getConnection()) {
            assertThat(connection).isNotNull();
        }
    }

}

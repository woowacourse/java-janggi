package janggi.infrastructure.db;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;

class DatabaseConnectionTest {

    @Test
    void 데이터베이스_커넥션을_생성한다() throws SQLException {
        Connection connection = DatabaseConnection.getConnection();

        assertThat(connection).isNotNull();

        connection.close();
    }
}

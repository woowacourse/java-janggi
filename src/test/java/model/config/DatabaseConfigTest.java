package model.config;

import config.DatabaseConfig;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class DatabaseConfigTest {
    @Test
    void 데이터베이스에_연결할_수_있다() throws SQLException {
        Connection connection = DatabaseConfig.getConnection();

        assertThat(connection).isNotNull();
        assertThat(connection.isClosed()).isFalse();

        connection.close();
    }
}

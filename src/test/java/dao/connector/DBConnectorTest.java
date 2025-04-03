package dao.connector;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

class DBConnectorTest {

    private final DBConnector dbConnector = new H2DBConnector();

    @Test
    void 테스트용_H2_DB_연결을_얻어온다() {
        try (final Connection connection = dbConnector.getConnection()) {
            assertThat(connection).isNotNull();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

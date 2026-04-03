package janggi.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import org.junit.jupiter.api.Test;

class DBConnectionManagerTest {

    @Test
    void DB_연결이_정상적으로_생성된다() {
        Connection connection = DBConnectionManager.getConnection();
        assertThat(connection).isNotNull();
    }
}

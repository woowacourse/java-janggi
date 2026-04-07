package janggi.repository.util;


import janggi.support.TestDBConnectionProvider;
import java.sql.Connection;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DBConnectionProviderTest {

    ConnectionProvider connectionProvider;

    @BeforeEach
    void setup() {
        connectionProvider = new TestDBConnectionProvider();
    }

    @Test
    @DisplayName("데이터베이스 커넥션을 획득한다.")
    void connectDatabase() {
        Connection conn = connectionProvider.getConnection();

        Assertions.assertThat(conn).isNotNull();
    }
}

package janggi.repository.util;


import janggi.repository.util.connection.ConnectionProvider;
import janggi.support.TestJdbcConnectionProvider;
import java.sql.Connection;
import java.sql.SQLException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JdbcConnectionProviderTest {

    ConnectionProvider connectionProvider;
    Connection conn;

    @BeforeEach
    void setup() {
        connectionProvider = new TestJdbcConnectionProvider();
    }

    @AfterEach
    void tearDown() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException("테스트 커넥션 종료 실패", e);
            }
        }
    }

    @Test
    @DisplayName("데이터베이스 커넥션을 획득한다.")
    void connectDatabase() {
        // given
        conn = connectionProvider.getConnection();

        // when & then
        Assertions.assertThat(conn).isNotNull();
    }
}

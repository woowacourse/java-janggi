package janggi.repository.util;

import janggi.repository.util.connection.ConnectionProvider;
import janggi.repository.util.transaction.JdbcTransactionManager;
import janggi.support.TestJdbcConnectionProvider;
import java.sql.Connection;
import java.sql.SQLException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JdbcTransactionManagerTest {

    JdbcTransactionManager transactionManager;

    @BeforeEach
    void setup() {
        ConnectionProvider connectionProvider = new TestJdbcConnectionProvider();
        transactionManager = new JdbcTransactionManager(connectionProvider);
    }

    @Test
    @DisplayName("트랜잭션을 시작하면 Connection의 AutoCommit 설정이 false로 변경된다.")
    void begin_SetsAutoCommitFalse() {
        // when & then
        transactionManager.executeInTransaction(() -> {
            Connection conn = transactionManager.getConnection();
            try {
                Assertions.assertThat(conn.getAutoCommit()).isFalse();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    @DisplayName("트랜잭션을 시작하지 않고 getConnection을 호출하면 IllegalStateException이 발생한다.")
    void getConnection_ThrowsException_WhenTransactionNotStarted() {
        // when & then
        Assertions.assertThatThrownBy(() -> transactionManager.getConnection())
                .isInstanceOf(IllegalStateException.class);
    }

}

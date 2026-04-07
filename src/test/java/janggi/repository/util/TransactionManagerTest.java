package janggi.repository.util;

import janggi.support.TestDBConnectionProvider;
import java.sql.Connection;
import java.sql.SQLException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TransactionManagerTest {

    TransactionManager transactionManager;

    @BeforeEach
    void setup() {
        ConnectionProvider connectionProvider = new TestDBConnectionProvider();
        transactionManager = new TransactionManager(connectionProvider);
    }

    @AfterEach
    void tearDown() {
        transactionManager.close();
    }

    @Test
    @DisplayName("트랜잭션을 시작하면 Connection의 AutoCommit 설정이 false로 변경된다.")
    void begin_SetsAutoCommitFalse() throws SQLException {
        // when
        transactionManager.begin();
        Connection connection = transactionManager.getConnection();

        // then
        Assertions.assertThat(connection.getAutoCommit()).isFalse();
    }

    @Test
    @DisplayName("트랜잭션을 시작하지 않고 getConnection을 호출하면 IllegalStateException이 발생한다.")
    void getConnection_ThrowsException_WhenTransactionNotStarted() {
        // when & then
        Assertions.assertThatThrownBy(() -> transactionManager.getConnection())
                .isInstanceOf(IllegalStateException.class);
    }

}

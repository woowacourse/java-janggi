package transaction;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.connector.TransactionalConnector;

class JdbcTransactionManagerTest {

    @DisplayName("트랜잭션 작업이 성공하면 커밋하고 정리한다.")
    @Test
    void 트랜잭션_작업이_성공하면_커밋하고_정리한다() {
        FakeTransactionalConnector connector = new FakeTransactionalConnector();
        JdbcTransactionManager transactionManager = new JdbcTransactionManager(connector);

        String result = transactionManager.execute(() -> "success");

        assertThat(result).isEqualTo("success");
        assertThat(connector.events).containsExactly("begin", "commit", "close");
    }

    @DisplayName("트랜잭션 작업이 실패하면 롤백하고 예외를 다시 던진다.")
    @Test
    void 트랜잭션_작업이_실패하면_롤백하고_예외를_다시_던진다() {
        FakeTransactionalConnector connector = new FakeTransactionalConnector();
        JdbcTransactionManager transactionManager = new JdbcTransactionManager(connector);

        assertThatThrownBy(() -> transactionManager.execute(() -> {
            throw new IllegalStateException("failure");
        })).isInstanceOf(IllegalStateException.class)
            .hasMessage("failure");

        assertThat(connector.events).containsExactly("begin", "rollback", "close");
    }

    @DisplayName("이미 트랜잭션이 진행 중이면 새 트랜잭션을 시작하지 않는다.")
    @Test
    void 이미_트랜잭션이_진행_중이면_새_트랜잭션을_시작하지_않는다() {
        FakeTransactionalConnector connector = new FakeTransactionalConnector();
        connector.activeTransaction = true;
        JdbcTransactionManager transactionManager = new JdbcTransactionManager(connector);

        String result = transactionManager.execute(() -> "nested");

        assertThat(result).isEqualTo("nested");
        assertThat(connector.events).isEmpty();
    }

    private static class FakeTransactionalConnector implements TransactionalConnector {

        private final List<String> events = new ArrayList<>();
        private boolean activeTransaction;

        @Override
        public void beginTransaction() {
            events.add("begin");
            activeTransaction = true;
        }

        @Override
        public void commitTransaction() {
            events.add("commit");
        }

        @Override
        public void rollbackTransaction() {
            events.add("rollback");
        }

        @Override
        public void closeTransaction() {
            events.add("close");
            activeTransaction = false;
        }

        @Override
        public boolean hasActiveTransaction() {
            return activeTransaction;
        }

        @Override
        public Connection getConnection() throws SQLException {
            throw new UnsupportedOperationException();
        }
    }
}

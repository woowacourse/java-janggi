package janggi.db;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.SQLException;
import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TransactionManagerTest {

    private static final String URL = "jdbc:h2:mem:test-db;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    private JdbcConnectionPool connectionPool;
    private TransactionManager transactionManager;

    @BeforeEach
    void setUp() {
        connectionPool = JdbcConnectionPool.create(
                URL,
                USER,
                PASSWORD
        );
        ConnectionManager connectionManager = new ConnectionManager(connectionPool);
        transactionManager = new TransactionManager(connectionManager);
    }

    @AfterEach
    void clear() {
        connectionPool.dispose();
    }

    @DisplayName("try-with-resources 종료 후 커넥션이 반납되고 풀은 다시 사용할 수 있다.")
    @Test
    void 트랜잭션_매니저_커넥션_반납_확인() throws SQLException {
        // 현재 활성 커넥션 수 0개 확인
        assertThat(connectionPool.getActiveConnections()).isZero();

        // 트랜잭션 시작 시 활성 커넥션 수가 1개로 늘어남을 확인
        Connection[] usedConnection = new Connection[1];
        transactionManager.executeWithTransaction(connection -> {
            usedConnection[0] = connection;
            assertThat(connectionPool.getActiveConnections()).isEqualTo(1);
        });

        // 트랜잭션 종료 후 활성 커넥션 수가 다시 0개로 줄어듬을 통해 커넥션을 반납했음과 사용했던 커넥션이 닫혔음을 확인
        assertThat(connectionPool.getActiveConnections()).isZero();
        assertThat(usedConnection[0].isClosed()).isTrue();

        // 트랜잭션을 다시 시작할 때 커넥션 풀에서 다시 커넥션을 가져옴으로써 커넥션 풀 사용이 가능함을 확인
        transactionManager.executeWithTransaction(connection -> {
            assertThat(connectionPool.getActiveConnections()).isEqualTo(1);
        });
    }
}

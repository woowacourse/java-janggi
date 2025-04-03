package janggi.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

class TransactionTest {

    private static final String DB_URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1"; // 커넥선 유지함

    @Test
    @DisplayName("정상 수행 시 데이터 반영")
    void execute_success_with_realDatabase() throws SQLException {
        // given
        final Connection conn = DriverManager.getConnection(DB_URL);
        conn.createStatement().execute("DROP TABLE IF EXISTS test_table");
        conn.createStatement().execute("CREATE TABLE test_table (id INT PRIMARY KEY)");

        final Transaction transaction = new Transaction();

        // when
        transaction.execute(conn, c -> {
            try {
                c.createStatement().execute("INSERT INTO test_table (id) VALUES (1)");
            } catch (final SQLException e) {
                throw new RuntimeException(e);
            }
        });

        // then
        final ResultSet rs = conn.createStatement().executeQuery("SELECT COUNT(*) FROM test_table");
        rs.next();
        final int count = rs.getInt(1);
        assertThat(count).isEqualTo(1);
    }

    @Test
    @DisplayName("트랜잭션 중 예외 발생 시 rollback 되어 DB에 반영되지 않아야 한다")
    void rollback_with_realDatabase() throws SQLException {
        // given
        final Connection connection = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        connection.createStatement().execute("CREATE TABLE test_table (id INT PRIMARY KEY)");

        final Transaction transaction = new Transaction();

        // when
        try {
            transaction.execute(connection, c -> {
                try {
                    c.createStatement().execute("INSERT INTO test_table (id) VALUES (1)");
                } catch (final SQLException e) {
                    throw new RuntimeException(e);
                }

                throw new RuntimeException("임의 예외 발생");
            });
        } catch (final RuntimeException ignored) {
        }

        // then
        final ResultSet rs = connection.createStatement().executeQuery("SELECT COUNT(*) FROM test_table");
        rs.next();
        final int count = rs.getInt(1);

        assertThat(count).isZero();
    }
}

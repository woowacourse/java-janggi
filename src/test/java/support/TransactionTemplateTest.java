package support;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.MemoryDBConnectionUtil;

@DisplayName("트랜잭션 테스트")
class TransactionTemplateTest {

    private DataSource dataSource;
    private TransactionTemplate transactionTemplate;

    @BeforeEach
    void setUp() throws SQLException {
        dataSource = MemoryDBConnectionUtil.getDataSource();
        transactionTemplate = new TransactionTemplate(dataSource);

        try (
                Connection conn = dataSource.getConnection();
                Statement stmt = conn.createStatement()
        ) {
            stmt.execute("CREATE TABLE IF NOT EXISTS tx_test (value INTEGER)");
            stmt.execute("DELETE FROM tx_test");
        }
    }

    @DisplayName("작업이 성공하면 커밋되어 결과가 반영된다")
    @Test
    void commit() {
        transactionTemplate.execute(conn -> {
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("INSERT INTO tx_test VALUES (1)");
            } catch (SQLException e) {
                throw new DataAccessException(e);
            }

            return null;
        });

        int count = countRows();
        assertThat(count).isEqualTo(1);
    }

    @DisplayName("작업 도중 DataAccessException이 발생하면 롤백되어 결과가 반영되지 않는다")
    @Test
    void rollback_on_data_access_exception() {
        assertThatThrownBy(() ->
                transactionTemplate.execute(conn -> {
                    try (Statement stmt = conn.createStatement()) {
                        stmt.execute("INSERT INTO tx_test VALUES (1)");
                    } catch (SQLException e) {
                        throw new DataAccessException(e);
                    }
                    throw new DataAccessException("강제 실패");
                })
        ).isInstanceOf(DataAccessException.class);

        assertThat(countRows()).isZero();
    }

    @DisplayName("도메인 로직으로 인한 예외 발생 때도 롤백된다")
    @Test
    void rollback_on_runtime_exception() {
        assertThatThrownBy(() ->
                transactionTemplate.execute(conn -> {
                    try (Statement stmt = conn.createStatement()) {
                        stmt.execute("INSERT INTO tx_test VALUES (1)");
                    } catch (SQLException e) {
                        throw new DataAccessException(e);
                    }

                    throw new IllegalArgumentException("잘못된 이동입니다.");
                })
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 이동입니다.");

        assertThat(countRows()).isZero();
    }

    @DisplayName("execute()의 반환값이 콜백의 반환값과 같다")
    @Test
    void return_value() {
        String result = transactionTemplate.execute(conn -> "success");

        assertThat(result).isEqualTo("success");
    }

    private int countRows() {
        try (
                Connection conn = dataSource.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM tx_test")
        ) {
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }
}

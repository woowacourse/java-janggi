import dao.JdbcConnection;
import java.sql.SQLException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class JdbcConnectionTest {

    private final JdbcConnection jdbcConnection = new JdbcConnection();

    @Test
    public void connection() throws SQLException {
        try (final var connection = jdbcConnection.getConnection()) {
            Assertions.assertThat(jdbcConnection).isNotNull();
        }
    }
}

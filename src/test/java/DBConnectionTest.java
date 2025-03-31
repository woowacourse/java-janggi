import static org.assertj.core.api.Assertions.assertThat;

import janggi.DBConnection;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;

class DBConnectionTest {

    @Test
    public void connection() throws SQLException {
        try (final var connection = DBConnection.getConnection()) {
            assertThat(connection).isNotNull();
        }
    }
}

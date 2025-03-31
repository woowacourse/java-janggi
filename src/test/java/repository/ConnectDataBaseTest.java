package repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;
import org.junit.jupiter.api.Test;
import repository.connection.ConnectDatabase;

public class ConnectDataBaseTest {

    private final ConnectDatabase connectH2 = new ConnectH2();

    @Test
    public void connection() {
        try (final var connection = connectH2.create()) {
            assertThat(connection).isNotNull();
            connectH2.close(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

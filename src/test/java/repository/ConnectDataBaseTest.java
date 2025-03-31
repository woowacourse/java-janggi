package repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;
import org.junit.jupiter.api.Test;
import repository.connection.ConnectDatabase;

public class ConnectDataBaseTest {

    private final ConnectDatabase connectMysql = new ConnectH2();

    @Test
    public void connection() {
        try (final var connection = connectMysql.create()) {
            assertThat(connection).isNotNull();
            connectMysql.close(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

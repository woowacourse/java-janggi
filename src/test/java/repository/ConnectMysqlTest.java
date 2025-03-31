package repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;
import org.junit.jupiter.api.Test;
import repository.connection.ConnectDatabase;
import repository.connection.ConnectMysql;

public class ConnectMysqlTest {

    private final ConnectDatabase connectMysql = new ConnectMysql();

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

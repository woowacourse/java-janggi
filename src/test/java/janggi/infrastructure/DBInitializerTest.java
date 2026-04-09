package janggi.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.jupiter.api.Test;

class DBInitializerTest {

    @Test
    void 테이블이_정상적으로_생성된다() throws SQLException {
        DBInitializer.initialize();

        try (Connection connection = DBConnectionManager.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet rs = statement.executeQuery(
                    "SELECT name FROM sqlite_master WHERE type='table' AND name='game'");
            assertThat(rs.next()).isTrue();

            rs = statement.executeQuery(
                    "SELECT name FROM sqlite_master WHERE type='table' AND name='piece'");
            assertThat(rs.next()).isTrue();
        }
    }

    @Test
    void 테이블_초기화를_여러번_호출해도_에러가_발생하지_않는다() {
        DBInitializer.initialize();
        DBInitializer.initialize();
    }
}

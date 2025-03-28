package janggi.util;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.dao.ConnectionUtil;
import java.sql.SQLException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ConnectionUtilTest {
    @DisplayName("실제 db connection이 정상적으로 되는지 테스트")
    @Test
    public void connection() {
        try (final var connection = ConnectionUtil.getConnection()) {
            assertThat(connection).isNotNull();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

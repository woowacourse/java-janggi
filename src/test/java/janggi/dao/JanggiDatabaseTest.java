package janggi.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JanggiDatabaseTest {

    /*
    해당 테스트가 실패한다면 데이터베이스 연결을 해야 합니다.
     */

    @DisplayName("데이터베이스 연결을 할 수 있다.")
    @Test
    void connection() {
        try (final var connection = JanggiDatabase.getConnection()) {
            assertThat(connection).isNotNull();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

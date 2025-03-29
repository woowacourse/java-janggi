package util;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DBConnectionUtilTest {

    @Test
    @DisplayName("커넥션 연결 테스트")
    void getConnection() {
        Connection connection = DBConnectionUtil.getConnection();
        assertThat(connection).isNotNull();
    }
}
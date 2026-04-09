package repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;
import javax.sql.DataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import support.DBConnectionUtil;

@DisplayName("DB 연결 테스트")
class DBConnectionUtilTest {

    @DisplayName("커넥션을 정상적으로 생성한다")
    @Test
    void 커넥션_정상_생성() throws SQLException {
        DataSource dataSource = DBConnectionUtil.getDataSource();

        assertThat(dataSource.getConnection()).isNotNull();
    }
}
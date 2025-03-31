package janggi.manager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.sql.SQLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Disabled
class DatabaseManagerTest {

    private DatabaseManager databaseManager;

    @BeforeEach
    void init() {
        databaseManager = DatabaseTestManager.create();
    }

    @DisplayName("데이터베이스 연결 테스트")
    @Test
    void connectionTest() {
        try (final var connection = databaseManager.getConnection()) {
            assertThat(connection).isNotNull();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("테이블이 존재하지 않으면 테이블을 생성한다.")
    @Test
    void test1() {
        assertThatCode(() -> databaseManager.createTableIfNotExist())
                .doesNotThrowAnyException();
    }
}

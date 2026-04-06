package repository.jdbc;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Connection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JdbcConnectionGeneratorTest {
    private static final String CONFIG_FILE_NAME = "database.properties";

    @Test
    @DisplayName("커네셕 생성자를 잘 생성한다")
    void create_success() {
        //when, then
        Assertions.assertDoesNotThrow(
                () -> JdbcConnectionGenerator.create(CONFIG_FILE_NAME)
        );
    }

    @Test
    @DisplayName("테스트 데이터베이스에 대한 Connection을 잘 제공한다")
    void getConnection_success() {
        //given
        JdbcConnectionGenerator connectionGenerator = JdbcConnectionGenerator.create(CONFIG_FILE_NAME);

        //when
        Connection dbConnection = connectionGenerator.getDBConnection();

        //then
        assertNotNull(dbConnection);
    }
}

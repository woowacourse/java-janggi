package service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.jdbc.JdbcConnectionGenerator;
import repository.jdbc.JdbcTemplate;

class JanggiGameServiceTest {

    private static final String DB_CONFIG_FILE = "database.properties";
    private static final JdbcConnectionGenerator CONNECTION_GENERATOR = JdbcConnectionGenerator.create(DB_CONFIG_FILE);

    private final JdbcTemplate jdbcTemplate = new JdbcTemplate();

    @Test
    @DisplayName("테이블 생성 시 문제가 발생하지 않는다")
    void test_initialize_success() {
        //given

        //when

        //then
    }
}

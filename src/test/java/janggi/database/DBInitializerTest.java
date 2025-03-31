package janggi.database;

import java.sql.SQLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

public class DBInitializerTest {

    DBConnector dbConnector;

    @BeforeEach
    void init() {
        dbConnector = new DBConnectorTest();
    }

    @DisplayName("테스트 DB 연결 테스트")
    @Test
    void test1() throws SQLException {
        try (final var connection = dbConnector.getConnection()) {
            assertThat(connection).isNotNull();
        }
    }

    @DisplayName("테이블 생성 테스트")
    @Test
    void test2() {
        //given
        DBInitializer dbInitializer = new DBInitializer(dbConnector);

        //when & then
        assertThatCode(dbInitializer::createTables).doesNotThrowAnyException();
    }
}

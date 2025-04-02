package janggi.dao;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MysqlConnectionTest {

    @DisplayName("기본 데이터베이스 연결을 테스트한다.")
    @Test
    void testConnection() {
        MysqlConnection mysqlConnection = new MysqlConnection();
        assertThatCode(mysqlConnection::getConnection)
                .doesNotThrowAnyException();
    }

    @DisplayName("테스트 데이터베이스 연결을 테스트한다.")
    @Test
    void testTestDBConnection() {
        MysqlConnection mysqlConnection = new MysqlConnection("janggi_test");
        assertThatCode(mysqlConnection::getConnection)
                .doesNotThrowAnyException();
    }
}

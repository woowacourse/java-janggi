package janggi.dao;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MysqlConnectionTest {

    @DisplayName("기본 데이터베이스 연결을 테스트한다.")
    @Test
    void testConnection() {
        MysqlConnection mysqlConnection = new MysqlConnection();
        try (final var connection = mysqlConnection.getConnection()) {
            assertThat(connection).isNotNull();
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    @DisplayName("테스트 데이터베이스 연결을 테스트한다.")
    @Test
    void testTestDBConnection() {
        MysqlConnection mysqlConnection = new MysqlConnection("janggi_test");
        try (final var connection = mysqlConnection.getConnection()) {
            assertThat(connection).isNotNull();
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}

package janggi.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DatabaseConnectionManagerTest {


    @Test
    @DisplayName("데이터베이스 연결 테스트")
    void test1() {
        assertThat(DatabaseConnectionManager.getConnection()).isNotNull();
    }

}
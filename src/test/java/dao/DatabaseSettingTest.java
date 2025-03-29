package dao;

import dao.init.ConnectionFactory;
import dao.init.DatabaseSetting;
import fixture.TestMySQLConnectionFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class DatabaseSettingTest {
    private final ConnectionFactory connectionFactory = new TestMySQLConnectionFactory();

    @Test
    void 데이터베이스_테이블_생성_초기화_테스트() {
        Assertions.assertThatCode(() -> DatabaseSetting.settingTable(connectionFactory.createConnection()));
    }
}
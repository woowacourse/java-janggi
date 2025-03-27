package dao;

import dao.init.DatabaseSetting;
import fixture.DatabaseConnectionFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class DatabaseSettingTest {

    @Test
    void 데이터베이스_테이블_생성_초기화_테스트() {
        Assertions.assertThatCode(() -> DatabaseSetting.settingTable(DatabaseConnectionFixture.getTestConnection()));
    }
}
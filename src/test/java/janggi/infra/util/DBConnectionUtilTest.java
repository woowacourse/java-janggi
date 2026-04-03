package janggi.infra.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DBConnectionUtilTest {
    
    @Test
    @DisplayName("DB Connection Test")
    public void getConnection() {

        // when then
        Assertions.assertThatCode(DBConnectionUtil::getConnection)
                .doesNotThrowAnyException();

    }

}

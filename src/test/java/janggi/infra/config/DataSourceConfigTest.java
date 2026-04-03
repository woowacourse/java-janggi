package janggi.infra.config;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DataSourceConfigTest {
    
    @Test
    @DisplayName("DB Connection Test")
    public void getConnection() {

        // when then
        Assertions.assertThatCode(() -> new DataSourceConfig().dataSource().getConnection())
                .doesNotThrowAnyException();

    }

}

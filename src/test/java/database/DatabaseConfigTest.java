package database;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DatabaseConfigTest {

    @Test
    void 기본_설정은_H2_파일_모드를_사용한다() {
        DatabaseConfig config = new DatabaseConfig();

        assertThat(config.jdbcUrl()).startsWith("jdbc:h2:file:");
        assertThat(config.jdbcUrl()).contains("storage");
        assertThat(config.jdbcUrl()).contains("janggi");
        assertThat(config.jdbcUrl()).contains("AUTO_SERVER=TRUE");
    }
}

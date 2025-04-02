package db;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("H2 DB 연결 테스트")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class H2DatabaseConnectorTest {

    @Test
    void _H2_DB_연결_테스트() {
        final H2DatabaseConnector connector = new H2DatabaseConnector();

        assertThat(connector.getConnection()).isNotNull();
    }
}

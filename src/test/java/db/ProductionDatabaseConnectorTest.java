package db;

import db.ProductionDatabaseConnector;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("프로덕션 DB 연결 테스트")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ProductionDatabaseConnectorTest {

    @Test
    void 프로덕션_DB_연결_테스트() {
        final ProductionDatabaseConnector connector = new ProductionDatabaseConnector();

        assertThat(connector.getConnection()).isNotNull();
    }
}

package domain.database;

import database.SchemaInitializer;
import database.connection.DBConnector;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

class DatabaseTest {

    private SchemaInitializer schemaInitializer;

    @BeforeEach
    void setUp() {
        schemaInitializer = new SchemaInitializer();
        schemaInitializer.readShemaSQLFile();
    }

    @Test
    @DisplayName("테스트에서 사용하는 데이터 베이스는 H2이다.")
    void h2DatabaseConnectionTest() throws Exception {
        // given
        try (Connection connection = DBConnector.getConnection()) {

            // when
            String expected = connection.getMetaData()
                    .getDatabaseProductName();

            // then
            Assertions.assertThat(expected)
                    .isEqualTo("H2");
        }
    }

}

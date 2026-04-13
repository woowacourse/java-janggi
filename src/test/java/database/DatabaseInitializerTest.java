package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DatabaseInitializerTest {

    @Test
    void 테스트에서는_H2_인메모리_모드로_스키마를_초기화한다() throws Exception {
        DatabaseConfig config = DatabaseConfig.inMemory("janggi-test");
        DatabaseInitializer databaseInitializer = new DatabaseInitializer(new ConnectionManager(config));

        databaseInitializer.initialize();

        try (Connection connection = new ConnectionManager(config).getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("""
                    SELECT COUNT(*)
                    FROM INFORMATION_SCHEMA.TABLES
                    WHERE TABLE_NAME IN ('GAME_SESSION', 'PIECE_SNAPSHOT')
                    """);

            assertThat(resultSet.next()).isTrue();
            assertThat(resultSet.getInt(1)).isEqualTo(2);
        }
    }
}

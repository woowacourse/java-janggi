package janggi.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DatabaseInitializerTest {

    @Test
    @DisplayName("init 호출 시 테이블 생성")
    void init() throws SQLException {
        // given
        JdbcConnectionManager connectionManager = new JdbcConnectionManager(
                "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
                "sa",
                ""
        );
        DatabaseInitializer databaseInitializer = new DatabaseInitializer(connectionManager);

        // when
        databaseInitializer.init();

        //then
        assertThat(existsTable(connectionManager, "GAMES")).isTrue();
        assertThat(existsTable(connectionManager, "GAME_PIECES")).isTrue();
    }

    private boolean existsTable(JdbcConnectionManager connectionManager, String tableName) throws SQLException {
        try (Connection connection = connectionManager.getConnection()){
            ResultSet resultSet = connection.getMetaData()
                    .getTables(null, null, tableName, null);
            return resultSet.next();
        }
    }
}

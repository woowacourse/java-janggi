package db;

import static org.assertj.core.api.Assertions.assertThat;

import db.connection.DBConnection;
import db.connection.MySQLConnection;
import java.sql.SQLException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DBConnectionTest {
    @DisplayName("DB 연결 테스트")
    @Test
    void getConnection() {
        //given
        DBConnection dbConnection = MySQLConnection.getInstance();

        // when // then
        try (final var connection = dbConnection.getConnection()) {
            assertThat(connection).isNotNull();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

package database;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import database.jdbc.DatabaseConnector;
import database.jdbc.DatabaseInitializer;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
class DatabaseConnectionTest {

    @Container
    static final MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("jangi-test")
            .withUsername("TESTUSER")
            .withPassword("1234");

    private DatabaseConnector connector() {
        return new DatabaseConnector(mysql.getJdbcUrl(), mysql.getUsername(), mysql.getPassword());
    }

    @Test
    void DB_연결이_성공한다() {
        assertThatCode(() -> connector().getConnection().close())
                .doesNotThrowAnyException();
    }

    @Test
    void 스키마_초기화가_성공한다() {
        assertThatCode(() -> DatabaseInitializer.initialize(connector()))
                .doesNotThrowAnyException();
    }

    @Test
    void game_테이블이_존재한다() throws Exception {
        DatabaseInitializer.initialize(connector());
        try (Connection conn = connector().getConnection()) {
            DatabaseMetaData meta = conn.getMetaData();
            ResultSet rs = meta.getTables(null, null, "game", new String[]{"TABLE"});
            assertThat(rs.next()).isTrue();
        }
    }

    @Test
    void piece_테이블이_존재한다() throws Exception {
        DatabaseInitializer.initialize(connector());
        try (Connection conn = connector().getConnection()) {
            DatabaseMetaData meta = conn.getMetaData();
            ResultSet rs = meta.getTables(null, null, "piece", new String[]{"TABLE"});
            assertThat(rs.next()).isTrue();
        }
    }
}

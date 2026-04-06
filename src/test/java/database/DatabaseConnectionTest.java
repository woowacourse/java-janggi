package database;

import database.jdbc.DatabaseConnector;
import database.jdbc.DatabaseInitializer;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class DatabaseConnectionTest {

    @Test
    void DB_연결이_성공한다() {
        assertThatCode(DatabaseConnector::getConnection)
                .doesNotThrowAnyException();
    }

    @Test
    void 스키마_초기화가_성공한다() {
        assertThatCode(DatabaseInitializer::initialize)
                .doesNotThrowAnyException();
    }

    @Test
    void game_테이블이_존재한다() throws Exception {
        try (Connection conn = DatabaseConnector.getConnection()) {
            DatabaseMetaData meta = conn.getMetaData();
            ResultSet rs = meta.getTables(null, null, "game", new String[]{"TABLE"});
            assertThat(rs.next()).isTrue();
        }
    }

    @Test
    void piece_테이블이_존재한다() throws Exception {
        try (Connection conn = DatabaseConnector.getConnection()) {
            DatabaseMetaData meta = conn.getMetaData();
            ResultSet rs = meta.getTables(null, null, "piece", new String[]{"TABLE"});
            assertThat(rs.next()).isTrue();
        }
    }
}

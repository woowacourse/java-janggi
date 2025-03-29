package janggi.database;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.sql.Connection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JanggiDatabaseTest {

    @DisplayName("커넥션 생성")
    @Test
    void getConnection() {
        // given
        final JanggiDatabase database = new JanggiDatabase();

        // when
        final Connection connection = database.getConnection();

        // then
        assertThat(connection).isNotNull();
    }

    @DisplayName("테이블 생성")
    @Test
    void createTables() {
        // given
        final JanggiDatabase database = new JanggiDatabase();

        // when & then
        assertThatCode(() -> {
            database.createJanggiTables();
        }).doesNotThrowAnyException();
    }

    @DisplayName("테이블 존재 여부 반환")
    @Test
    void existsTable() {
        // given
        final JanggiDatabase database = new JanggiDatabase();
        createTables(database);

        // when
        final boolean actual = database.existsJanggiTable();

        // then
        assertThat(actual).isTrue();
    }

    private void createTables(final JanggiDatabase database) {
        try {
            database.createJanggiTables();
        } catch (final IllegalStateException e){
        }
    }
}

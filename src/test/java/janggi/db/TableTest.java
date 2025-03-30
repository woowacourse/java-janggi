package janggi.db;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.sql.SQLException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class TableTest {
    Connection connection = new Connection();
    Table table = new Table(connection);

    @Disabled
    @DisplayName("기물 테이블 생성 테스트")
    @Test
    void createPieceTableTest() {
        table.createPieceTable();
    }

    @Disabled
    @DisplayName("턴 테이블 생성 테스트")
    @Test
    void createTurnTableTest() {
        table.createTurnTable();
    }

    @Disabled
    @DisplayName("테이블이 존재하는지 확인")
    @ParameterizedTest
    @ValueSource(strings = {"piece", "turn"})
    void isTableExistTest(String tableName) throws SQLException {
        assertThat(table.isTableExist(tableName)).isTrue();
    }

    @Disabled
    @DisplayName("테이블 삭제 테스트")
    @ParameterizedTest
    @ValueSource(strings = {"piece", "turn"})
    void isTableDropTest(String tableName) throws SQLException {
        table.dropTable(tableName);
    }
}

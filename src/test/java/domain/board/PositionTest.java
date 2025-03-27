package domain.board;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PositionTest {
    @Test
    void 다른_포지션과의_row_차이를_구한다() {
        Position src = new Position(Row.FOUR, Column.FOUR);
        Position dst = new Position(Row.FIVE, Column.FOUR);

        assertThat(src.rowDifference(dst)).isEqualTo(1);
    }

    @Test
    void 다른_포지션과의_column_차이를_구한다() {
        Position src = new Position(Row.FOUR, Column.FOUR);
        Position dst = new Position(Row.FIVE, Column.FIVE);

        assertThat(src.columnDifference(dst)).isEqualTo(1);
    }

    @ParameterizedTest
    @CsvSource({"1,4", "1,5", "1,6", "3,4", "3,5", "3,6"})
    void 궁성_영역_내부임을_정상적으로_확인(int rowValue, int columnValue) {
        Position position = new Position(Row.from(rowValue), Column.from(columnValue));
        assertThat(position.isInPalace()).isTrue();
    }

    @ParameterizedTest
    @CsvSource({"4,4", "4,5", "4,6", "8,3", "9,3", "10,3"})
    void 궁성_영역_외부임을_정상적으로_확인(int rowValue, int columnValue) {
        Position position = new Position(Row.from(rowValue), Column.from(columnValue));
        assertThat(position.isInPalace()).isFalse();
    }

}

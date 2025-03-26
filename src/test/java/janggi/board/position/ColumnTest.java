package janggi.board.position;

import static janggi.board.position.Column.EIGHT;
import static janggi.board.position.Column.ZERO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.board.PositionOutOfBoardBoundsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class ColumnTest {
    @CsvSource(value = {"0:ZERO", "1:ONE", "2:TWO", "3:THREE", "4:FOUR", "5:FIVE", "6:SIX", "7:SEVEN", "8:EIGHT"},
            delimiterString = ":")
    @ParameterizedTest
    void from(int value, Column expected) {
        // when
        Column result = Column.from(value);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @ValueSource(ints = {-1, Column.SIZE})
    @ParameterizedTest
    void should_ThrowException_WhenValueIsInvalid(int value) {
        // when
        // then
        assertThatThrownBy(() -> Column.from(value))
                .isInstanceOf(PositionOutOfBoardBoundsException.class)
                .hasMessage("[ERROR] 올바르지 않은 열입니다.");
    }

    @CsvSource(value = {"ZERO:ONE", "ONE:TWO", "TWO:THREE", "THREE:FOUR", "FOUR:FIVE", "FIVE:SIX", "SIX:SEVEN",
            "SEVEN:EIGHT"}, delimiterString = ":")
    @ParameterizedTest
    void up(Column column, Column expected) {
        // when
        Column result = column.up();

        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void should_ThrowException_WhenColumnCanNotUp() {
        // when
        // then
        assertThatThrownBy(() -> EIGHT.up())
                .isInstanceOf(PositionOutOfBoardBoundsException.class)
                .hasMessage("[ERROR] 더 이상 열을 증가할 수 없습니다.");
    }

    @CsvSource(value = {"ZERO:ONE", "ONE:TWO", "TWO:THREE", "THREE:FOUR", "FOUR:FIVE", "FIVE:SIX", "SIX:SEVEN",
            "SEVEN:EIGHT"}, delimiterString = ":")
    @ParameterizedTest
    void down(Column expected, Column column) {
        // when
        Column result = column.down();

        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void should_ThrowException_WhenColumnCanNotDown() {
        // when
        // then
        assertThatThrownBy(() -> ZERO.down())
                .isInstanceOf(PositionOutOfBoardBoundsException.class)
                .hasMessage("[ERROR] 더 이상 열을 감소할 수 없습니다.");
    }
}

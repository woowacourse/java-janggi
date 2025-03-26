package janggi.board.position;

import static janggi.board.position.Row.NINE;
import static janggi.board.position.Row.ZERO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.board.PositionOutOfBoardBoundsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class RowTest {
    @CsvSource(value = {"0:ZERO", "1:ONE", "2:TWO", "3:THREE", "4:FOUR", "5:FIVE", "6:SIX", "7:SEVEN", "8:EIGHT",
            "9:NINE"}, delimiterString = ":")
    @ParameterizedTest
    void from(int value, Row expected) {
        // when
        Row result = Row.from(value);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @ValueSource(ints = {-1, Row.SIZE})
    @ParameterizedTest
    void should_ThrowException_WhenValueIsInvalid(int value) {
        // when
        // then
        assertThatThrownBy(() -> Row.from(value))
                .isInstanceOf(PositionOutOfBoardBoundsException.class)
                .hasMessage("[ERROR] 올바르지 않은 행입니다.");
    }

    @CsvSource(value = {"ZERO:ONE", "ONE:TWO", "TWO:THREE", "THREE:FOUR", "FOUR:FIVE", "FIVE:SIX", "SIX:SEVEN",
            "SEVEN:EIGHT", "EIGHT:NINE"}, delimiterString = ":")
    @ParameterizedTest
    void up(Row row, Row expected) {
        // when
        Row result = row.up();

        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void should_ThrowException_WhenRowCanNotUp() {
        // when
        // then
        assertThatThrownBy(() -> NINE.up())
                .isInstanceOf(PositionOutOfBoardBoundsException.class)
                .hasMessage("[ERROR] 더 이상 행을 증가할 수 없습니다.");
    }

    @CsvSource(value = {"ZERO:ONE", "ONE:TWO", "TWO:THREE", "THREE:FOUR", "FOUR:FIVE", "FIVE:SIX", "SIX:SEVEN",
            "SEVEN:EIGHT", "EIGHT:NINE"}, delimiterString = ":")
    @ParameterizedTest
    void down(Row expected, Row row) {
        // when
        Row result = row.down();

        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void should_ThrowException_WhenRowCanNotDown() {
        // when
        // then
        assertThatThrownBy(() -> ZERO.down())
                .isInstanceOf(PositionOutOfBoardBoundsException.class)
                .hasMessage("[ERROR] 더 이상 행을 감소할 수 없습니다.");
    }
}

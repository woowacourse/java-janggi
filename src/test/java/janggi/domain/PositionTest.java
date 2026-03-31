package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.exception.ExceptionMessage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class PositionTest {

    private static final int MIN_POSITION_INDEX = 0;
    private static final int MAX_ROW_INDEX = 9;
    private static final int MAX_COLUMN_INDEX = 8;

    @ParameterizedTest
    @CsvSource(value = {
            "-1,8",
            "10,1",
    })
    void 포지션의_행이_0부터_9행까지가_아닐_경우_예외가_발생한다(int row, int col) {
        Assertions.assertThatThrownBy(() -> new Position(row, col))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionMessage.ROW_OUT_OF_RANGE.getMessage(MIN_POSITION_INDEX, MAX_ROW_INDEX));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "0,9",
            "0,-1",
    })
    void 포지션의_열이_0부터_8열까지가_아닐_경우_예외가_발생한다(int row, int col) {
        Assertions.assertThatThrownBy(() -> new Position(row, col))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionMessage.COLUMN_OUT_OF_RANGE.getMessage(MIN_POSITION_INDEX, MAX_COLUMN_INDEX));
    }

    @Test
    void 행의_차이를_계산한다() {
        //given
        Position source = new Position(5, 3);
        Position destination = new Position(3, 3);
        //when
        int result = source.calculateRowDistance(destination);
        //then
        assertThat(result).isEqualTo(2);
    }

    @Test
    void 열의_차이를_계산한다() {
        //given
        Position source = new Position(5, 3);
        Position destination = new Position(5, 0);
        //when
        int result = source.calculateColumnDistance(destination);
        //then
        assertThat(result).isEqualTo(3);
    }
}

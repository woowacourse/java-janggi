package domain.board;

import common.exception.JanggiException;
import domain.board.exception.FormationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static domain.board.exception.ErrorMessage.FORMATION_IS_NOT_NUMERIC;
import static domain.board.exception.ErrorMessage.FORMATION_NUMBER_RANGE_IS_INVALID;

public class FormationTest {

    @Test
    @DisplayName("입력값이 숫자가 아닌 경우, 예외가 발생한다.")
    void shouldThrowExceptionWhenInputIsNotNumber() {
        String notNumberInput = "ㄱ";

        Assertions.assertThatThrownBy(() -> {
                    Formation.from(notNumberInput);
                }).isInstanceOf(FormationException.class)
                .hasMessage(FORMATION_IS_NOT_NUMERIC.getErrorMessage());
    }

    @Test
    @DisplayName("1부터 4사이의 숫자가 아닌 경우, 예외가 발생한다.")
    void shouldThrowExceptionWhenFormationNumberIsNotBetween1to4() {
        String lessThanOne = "0";
        String overThanFour = "5";

        Assertions.assertThatThrownBy(() -> {
                    Formation.from(lessThanOne);
                }).isInstanceOf(FormationException.class)
                .hasMessage(FORMATION_NUMBER_RANGE_IS_INVALID.getErrorMessage());

        Assertions.assertThatThrownBy(() -> {
                    Formation.from(overThanFour);
                }).isInstanceOf(FormationException.class)
                .hasMessage(FORMATION_NUMBER_RANGE_IS_INVALID.getErrorMessage());
    }

}

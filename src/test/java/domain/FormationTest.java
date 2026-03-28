package domain;

import domain.board.Formation;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FormationTest {

    @Test
    @DisplayName("입력값이 숫자가 아닌 경우, 예외가 발생한다.")
    void shouldThrowExceptionWhenInputIsNotNumber() {
        String notNumberInput = "ㄱ";

        Assertions.assertThatThrownBy(() -> {
            Formation.from(notNumberInput);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("1부터 4사이의 숫자가 아닌 경우, 예외가 발생한다.")
    void shouldThrowExceptionWhenFormationNumberIsNotBetween1to4() {
        String lessThanOne = "0";
        String overThanFour = "5";

        Assertions.assertThatThrownBy(() -> {
            Formation.from(lessThanOne);
        }).isInstanceOf(IllegalArgumentException.class);

        Assertions.assertThatThrownBy(() -> {
            Formation.from(overThanFour);
        }).isInstanceOf(IllegalArgumentException.class);
    }

}

package janggi.util;

import static janggi.util.InputValidator.validateHorseElephantPositionOrdinal;
import static janggi.util.InputValidator.validatePosition;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class InputValidatorTest {

    @ParameterizedTest
    @ValueSource(strings = {"1", "2", "3", "4"})
    public void 마와_상의_차림법을_1에서_4까지의_숫자로_입력받는다(String input) {
        // when & then
        assertThatCode(() -> validateHorseElephantPositionOrdinal(input))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", " ", "-", "one"})
    public void 마와_상의_차림법이_숫자가_아니면_오류를_일으킨다(String input) {
        // when & then
        assertThatThrownBy(() -> validateHorseElephantPositionOrdinal(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("숫자로 입력해주세요.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"-1", "0", "5"})
    public void 마와_상의_차림법이_1에서_4까지의_수가_아니면_오류를_일으킨다(String input) {
        // when & then
        assertThatThrownBy(() -> validateHorseElephantPositionOrdinal(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("1, 2, 3, 4 중 하나의 숫자를 입력해주세요.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"1,1", "10,9", "1, 3", " 2, 4"})
    public void 위치_정보를_콤마로_구분된_두_숫자로_입력받는다(String input) {
        // when & then
        assertThatCode(() -> validatePosition(input))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(strings = {"1.3", "2'1", "31", "4 3"})
    public void 위치_정보가_콤마로_구분했을때_두개가_아니면_오류를_일으킨다(String input) {
        // when & then
        assertThatThrownBy(() -> validatePosition(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("콤마로 구분된 두 개의 숫자를 올바르게 입력해주세요.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"a,3", "2, ", "3,q", "4,-"})
    public void 위치_정보를_콤마로_구분했을때_숫자가_아니면_오류를_일으킨다(String input) {
        // when & then
        assertThatThrownBy(() -> validatePosition(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("숫자로 입력해주세요.");
    }

}

package exception;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

public class ValidatorTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "kang",
            "\t",
            " ",
            "-2200000000",
            "2200000000"
    })
    void 정수_변환_예외_테스트(String input) {
        assertThatThrownBy(() -> Validator.validateNumber(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("숫자가 아닙니다.");
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 정수_변환_빈값_예외_테스트(String input) {
        assertThatThrownBy(() -> Validator.validateNumber(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("숫자가 아닙니다.");
    }

    @ParameterizedTest
    @CsvSource(value = {
            "123, 123",
            "-123, -123",
            "00, 0"
    })
    void 정수_변환_정상_테스트(String input, int expectedNumber) {
        assertThat(Validator.validateNumber(input)).isEqualTo(expectedNumber);

        assertThatCode(() -> Validator.validateNumber(input))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1, 4, 5",
            "1, 4, 0",
            "1, 4, -1"
    })
    void 정수_범위_검증_예외_테스트(int min, int max, int input) {
        assertThatThrownBy(() -> Validator.validateNumberInRange(min, max, input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("범위 내의 숫자가 아닙니다.");
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1, 4, 4",
            "1, 4, 1",
            "0, 1, 0"
    })
    void 정수_범위_검증_정상_테스트(int min, int max, int input) {
        assertThatCode(() -> Validator.validateNumberInRange(min, max, input))
                .doesNotThrowAnyException();
    }
}

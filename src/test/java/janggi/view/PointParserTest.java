package janggi.view;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class PointParserTest {

    @DisplayName("Point로 파싱할 수 있다.")
    @ParameterizedTest
    @ValueSource(strings = {"11", "05", "50", "89"})
    void parseTest(String input) {
        // when & then
        assertThatCode(() -> PointParser.parse(input))
                .doesNotThrowAnyException();
    }

    @DisplayName("잘못된 좌표계를 입력한 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"110", "-13"})
    void shouldThrowException_WhenInvalidInput(String invalidInput) {
        // when & then
        assertThatCode(() -> PointParser.parse(invalidInput))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 좌표 입력입니다. 입력: %s".formatted(invalidInput));
    }

    @DisplayName("숫자가 아닌 값을 입력한 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"A1", "3B"})
    void shouldThrowException_WhenInvalidInputWithNotNumeric(String invalidInput) {
        // when & then
        assertThatCode(() -> PointParser.parse(invalidInput))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("숫자만 입력 가능합니다. 입력: %s".formatted(invalidInput));
    }

    @DisplayName("null 또는 빈 문자열을 입력한 경우 예외가 발생한다.")
    @ParameterizedTest
    @NullAndEmptySource
    void shouldThrowException_WhenNullOrEmpty(String emptyInput) {
        // when & then
        assertThatCode(() -> PointParser.parse(emptyInput))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("입력이 비어있습니다.");
    }
}

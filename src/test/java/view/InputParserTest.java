package view;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class InputParserTest {
    @ParameterizedTest
    @DisplayName("좌표 형식이 잘못된 경우 예외가 발생한다.")
    @ValueSource(strings = {",12", "a,b", "1:2"})
    void positionFormatExceptionTest(String input) {
        assertThatThrownBy(() -> InputParser.parsePosition(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 잘못된 좌표 형식입니다.");
    }

    @ParameterizedTest
    @DisplayName("좌표의 개수가 2개가 아닌 경우 예외가 발생한다.")
    @ValueSource(strings = {",", "1,2,3"})
    void invalidCountExceptionTest(String input) {
        assertThatThrownBy(() -> InputParser.parsePosition(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 좌표는 2개만 입력해 주세요.");
    }
}

package util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ParserTest {

    @ParameterizedTest
    @ValueSource(strings = {"a", "10000000000000000000000", "ㅁ"})
    void Integer로_변환되지_않는_값은_예외를_발생한다(String input) {

        // when & then
        Assertions.assertThatThrownBy(() -> Parser.parseInput(input))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("유효한 입력값이 아닙니다.");
    }
}
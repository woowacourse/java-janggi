package domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class InputParserTest {

    @ParameterizedTest
    @ValueSource(strings = {"(0,3)", "(3,  9)"})
    void 올바른_좌표가_입력되는_경우(String input) {
        assertThatCode(() -> InputParser.parsePosition(input))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "0,3", "a,b"})
    void 올바른_좌표가_입력되지_않는_경우(String input) {
        assertThatThrownBy(() -> InputParser.parsePosition(input))
                .isInstanceOf(IllegalArgumentException.class);
    }
}

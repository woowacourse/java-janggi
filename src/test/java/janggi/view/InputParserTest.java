package janggi.view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.board.FormationCommand;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class InputParserTest {

    @ParameterizedTest
    @ValueSource(strings = {"0,3", "3,  9"})
    void 올바른_좌표_형식이_입력되는_경우_정상_동작한다(String input) {
        assertThatCode(() -> InputParser.parsePosition(input))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "(0,3)", "(a,b)", "a,b", "0", "(,)"})
    void 위치_입력_포맷이_올바른_형태가_아니면_예외가_발생한다(String input) {
        assertThatThrownBy(() -> InputParser.parsePosition(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {"1:FIRST", "2:SECOND", "3:THIRD", "4:FOURTH"}, delimiter = ':')
    void 포메이션_번호를_입력하면_해당하는_커맨드로_변환한다(String input, FormationCommand expected) {
        assertThat(InputParser.parseFormation(input)).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "5", "a"})
    void 올바르지_않은_포메이션_번호_입력_시_예외가_발생한다(String input) {
        assertThatThrownBy(() -> InputParser.parseFormation(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("올바른 포메이션 입력이 아닙니다.");
    }
}

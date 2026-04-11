package view.command;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CommandTypeTest {

    @ParameterizedTest
    @ValueSource(strings = {"move", "m", "MOVE", "M", " move ", " m ", "Move"})
    void move_명령을_파싱한다(String input) {
        assertThat(CommandType.from(input)).isEqualTo(CommandType.MOVE);
    }

    @ParameterizedTest
    @ValueSource(strings = {"score", "s", "SCORE", "S", " score ", " s ", "Score"})
    void score_명령을_파싱한다(String input) {
        assertThat(CommandType.from(input)).isEqualTo(CommandType.SCORE);
    }

    @ParameterizedTest
    @ValueSource(strings = {"quit", "q", "QUIT", "Q", " quit ", " q ", "Quit"})
    void quit_명령을_파싱한다(String input) {
        assertThat(CommandType.from(input)).isEqualTo(CommandType.QUIT);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "invalid", "mov", "exit", "start", "do something"})
    void 존재하지_않는_명령이면_예외를_던진다(String input) {
        assertThatThrownBy(() -> CommandType.from(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 명령입니다.");
    }
}

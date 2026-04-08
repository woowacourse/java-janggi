package view.parser;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.board.Formation;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.Test;

class InputParserTest {

    @ParameterizedTest
    @ValueSource(strings = {"0,3", "3,  9", "  8 ,0 "})
    void 올바른_좌표_형식이_입력되는_경우_정상_동작한다(String input) {
        assertThatCode(() -> InputParser.parsePosition(input))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "a,b", "(0,3)", "(a,b)", "()", "(,)", "1", "1,2,3"})
    void 위치_입력_포맷이_올바른_형태가_아니면_예외가_발생한다(String input) {
        assertThatThrownBy(() -> InputParser.parsePosition(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 포메이션_입력을_도메인_포메이션으로_파싱한다() {
        assertThat(InputParser.parseFormation("1")).isEqualTo(Formation.LEFT_ELEPHANT);
        assertThat(InputParser.parseFormation("2")).isEqualTo(Formation.RIGHT_ELEPHANT);
        assertThat(InputParser.parseFormation("3")).isEqualTo(Formation.OUTER_ELEPHANT);
        assertThat(InputParser.parseFormation("4")).isEqualTo(Formation.INNER_ELEPHANT);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "0", "5", "a"})
    void 잘못된_포메이션_입력이면_예외가_발생한다(String input) {
        assertThatThrownBy(() -> InputParser.parseFormation(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 시작_메뉴_입력을_파싱한다() {
        assertThat(InputParser.parseGameStartCommand("1")).isEqualTo(GameStartCommand.NEW_GAME);
        assertThat(InputParser.parseGameStartCommand("2")).isEqualTo(GameStartCommand.LOAD_GAME);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "0", "3", "a"})
    void 잘못된_시작_메뉴_입력이면_예외가_발생한다(String input) {
        assertThatThrownBy(() -> InputParser.parseGameStartCommand(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 메뉴_번호_입력을_범위에_맞게_파싱한다() {
        assertThat(InputParser.parseMenuNumber("1", 1, 3)).isEqualTo(1);
        assertThat(InputParser.parseMenuNumber(" 3 ", 1, 3)).isEqualTo(3);
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "4", "a"})
    void 메뉴_번호_입력이_범위를_벗어나거나_숫자가_아니면_예외가_발생한다(String input) {
        assertThatThrownBy(() -> InputParser.parseMenuNumber(input, 1, 3))
                .isInstanceOf(IllegalArgumentException.class);
    }
}

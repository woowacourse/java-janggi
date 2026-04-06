package janggi.util;

import static janggi.util.InputParser.parseGameId;
import static janggi.util.InputParser.parseHorseElephantPositionOrdinal;
import static janggi.util.InputParser.parsePosition;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class InputParserTest {

    @ParameterizedTest(name = "{0}은 올바른 장기 게임 아이디 형식이다.")
    @ValueSource(strings = {"1", "2", "10000"})
    public void 올바른_장기_게임_아이디를_입력받는다(String input) {
        // when
        long gameId = parseGameId(input);

        // then
        assertThat(gameId).isEqualTo(Long.parseLong(input));
    }

    @ParameterizedTest(name = "{0}은 올바르지않은 장기 게임 아이디 형식이다.")
    @ValueSource(strings = {"aa", "-", "hi"})
    public void 장기_게임_아이디가_숫자가_아니면_오류를_일으킨다(String input) {
        // when & then
        assertThatThrownBy(() -> parseGameId(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("숫자로 입력해주세요.");
    }

    @ParameterizedTest(name = "{0}은 올바른 마와 상의 상차림 법 입력이다.")
    @ValueSource(strings = {"1", "2", "3", "4"})
    public void 마와_상의_차림법을_1에서_4까지의_숫자로_입력받는다(String input) {
        // when
        int ordinal = parseHorseElephantPositionOrdinal(input);

        // then
        assertThat(ordinal).isEqualTo(Integer.parseInt(input));
    }

    @ParameterizedTest(name = "숫자가 아닌 {0}은 올바르지않은 마와 상의 상차림 법 입력이다.")
    @ValueSource(strings = {"a", " ", "-", "one"})
    public void 마와_상의_차림법이_숫자가_아니면_오류를_일으킨다(String input) {
        // when & then
        assertThatThrownBy(() -> parseHorseElephantPositionOrdinal(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("숫자로 입력해주세요.");
    }

    @ParameterizedTest(name = "1부터 4의 정수가 아닌 {0}은 올바르지않은 마와 상의 상차림 법 입력이다.")
    @ValueSource(strings = {"-1", "0", "5"})
    public void 마와_상의_차림법이_1에서_4까지의_수가_아니면_오류를_일으킨다(String input) {
        // when & then
        assertThatThrownBy(() -> parseHorseElephantPositionOrdinal(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("1, 2, 3, 4 중 하나의 숫자를 입력해주세요.");
    }

    @ParameterizedTest(name = "{0}은 콤마로 구분되는 올바른 위치 정보이다.")
    @ValueSource(strings = {"1,1", "10,9", "1, 3", " 2, 4"})
    public void 위치_정보를_콤마로_구분된_두_숫자로_입력받는다(String input) {
        // when
        List<Integer> position = parsePosition(input);
        List<Integer> expectPosition = Arrays.stream(input.split(","))
                .map(str -> Integer.parseInt(str.strip()))
                .toList();

        // then
        assertThat(position.size()).isEqualTo(2);
        assertThat(position).containsAnyElementsOf(expectPosition);
    }

    @ParameterizedTest(name = "행과 열, 2개가 아닌 {0}은 올바르지않은 위치 정보이다.")
    @ValueSource(strings = {"1.3", "2'1", "31", "4 3"})
    public void 위치_정보가_콤마로_구분했을때_두개가_아니면_오류를_일으킨다(String input) {
        // when & then
        assertThatThrownBy(() -> parsePosition(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("콤마로 구분된 두 개의 숫자를 올바르게 입력해주세요.");
    }

    @ParameterizedTest(name = "숫자가 아닌 문자를 포함한 {0}은 올바르지않은 위치 정보이다.")
    @ValueSource(strings = {"a,3", "2, ", "3,q", "4,-"})
    public void 위치_정보를_콤마로_구분했을때_숫자가_아니면_오류를_일으킨다(String input) {
        // when & then
        assertThatThrownBy(() -> parsePosition(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("숫자로 입력해주세요.");
    }

}

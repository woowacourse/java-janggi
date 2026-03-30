package janggi.view;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PositionParserTest {

    @Test
    void 좌표_문자열을_정수_리스트로_파싱한다() {
        List<Integer> actual = PositionParser.parsePositionInput("3,4");

        assertThat(actual).containsExactly(3, 4);
    }

    @Test
    void 좌표_문자열의_공백을_제거한_뒤_파싱한다() {
        List<Integer> actual = PositionParser.parsePositionInput(" 10, 9 ");

        assertThat(actual).containsExactly(10, 9);
    }

    @Test
    void 좌표_문자열에_숫자가_아닌_값이_있으면_예외가_발생한다() {
        assertThatThrownBy(() -> PositionParser.parsePositionInput("a,1"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("좌표는 숫자만 입력 가능합니다.");
    }
}


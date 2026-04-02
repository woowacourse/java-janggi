package janggi.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PositionTest {
    @ParameterizedTest
    @ValueSource(strings = {"0,7", "100,2", "-2,7"})
    void 입력한_행이_유효_범위를_벗어나면_예외가_발생한다(String input) {
        List<Integer> inputValue = Arrays.stream(input.split(","))
                .map(Integer::parseInt)
                .toList();
        assertThatThrownBy(() -> Position.from(inputValue)).isInstanceOf(IllegalArgumentException.class).hasMessage("유효하지 않은 위치입니다. 행은 1부터 10까지 가능합니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"2,12", "5,0", "3,-6"})
    void 입력한_열이_유효_범위를_벗어나면_예외가_발생한다(String input) {
        List<Integer> inputValue = Arrays.stream(input.split(","))
                .map(Integer::parseInt)
                .toList();
        assertThatThrownBy(() -> Position.from(inputValue)).isInstanceOf(IllegalArgumentException.class).hasMessage("유효하지 않은 위치입니다. 열은 1부터 9까지 가능합니다.");
    }

    @Test
    void 입력값_개수가_2개가_아니면_Position_생성에_실패한다() {
        List<Integer> inputValue1 = List.of(1);
        List<Integer> inputValue2 = List.of(3, 5, 1);

        assertThatThrownBy(() -> Position.from(inputValue1)).isInstanceOf(IllegalArgumentException.class).hasMessage("행과 열 두 개의 값만 입력하세요.");
        assertThatThrownBy(() -> Position.from(inputValue2)).isInstanceOf(IllegalArgumentException.class).hasMessage("행과 열 두 개의 값만 입력하세요.");
    }
}

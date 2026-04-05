package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class PositionTest {
    @Test
    void 정상적인_범위의_값이_입력되면_Position이_생성된다() {
        Position position = new Position(1, 9);

        assertThat(position.getX()).isEqualTo(1);
        assertThat(position.getY()).isEqualTo(9);
    }

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

    @Test
    void 좌표가_같으면_동등한_객체로_취급한다() {
        Position pos1 = new Position(5, 5);
        Position pos2 = new Position(5, 5);

        assertThat(pos1).isEqualTo(pos2);
    }

    @Test
    void 이동_시_판_범위를_벗어나면_예외가_발생한다() {
        Position start = new Position(1, 1);

        assertThatThrownBy(() -> start.move(Movement.UP))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유효하지 않은 위치입니다. 행은 1부터 10까지 가능합니다.");
    }

    @Test
    void 특정_범위_안에_있는지_확인한다() {
        Position position = new Position(2, 5);

        assertThat(position.isRange(1, 3, 1, 6)).isTrue();
        assertThat(position.isRange(2, 2, 5, 5)).isTrue();

        assertThat(position.isRange(1, 2, 1,4)).isFalse();
        assertThat(position.isRange(2,2, 1,4)).isFalse();
    }

    @Test
    void 두_좌표_사이의_방향을_계산한다() {
        Position start = new Position(5, 5);

        assertThat(start.calculateDirection(new Position(3, 5))).isEqualTo(Movement.UP);
        assertThat(start.calculateDirection(new Position(7, 7))).isEqualTo(Movement.DOWN_RIGHT);
    }

    @ParameterizedTest
    @CsvSource({
            "7, 6",
            "6, 9",
            "2, 4"
    })
    void 직선이나_대각선이_아닌_방향_계산_시_예외가_발생한다(int x, int y) {
        Position start = new Position(5, 5);
        Position invalidTarget = new Position(x, y);

        assertThatThrownBy(() -> start.calculateDirection(invalidTarget))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage(Movement.INVALID_DELTA_DIRECTION_MESSAGE);
    }

    @ParameterizedTest
    @CsvSource({
            "5, 8, 3",
            "3, 3, 2",
            "1, 5, 4"
    })
    void 두_직선_좌표_사이의_거리를_계산한다(int x, int y, int distance) {
        Position start = new Position(5, 5);

        assertThat(start.calculateLinearDistance(new Position(x, y))).isEqualTo(distance);
    }

}

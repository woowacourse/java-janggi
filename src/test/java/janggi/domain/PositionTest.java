package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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

    @ParameterizedTest
    @CsvSource({
            "5, 5, 1, 5, -1, 0",
            "5, 5, 9, 5, 1, 0",
            "5, 5, 5, 1, 0, -1",
            "5, 5, 5, 9, 0, 1"
    })
    void 상하좌우_직선에_대한_방향을_제대로_가져온다(int x1, int y1, int x2, int y2, int dx, int dy) {
        Position start = new Position(x1, y1);
        Position end = new Position(x2, y2);

        Movement movement = start.getLinearDirection(end);

        assertThat(movement.getDx()).isEqualTo(dx);
        assertThat(movement.getDy()).isEqualTo(dy);
    }

    @ParameterizedTest
    @CsvSource({
            "1, 4, 3, 6, 1, 1",
            "3, 6, 1, 4, -1, -1",
            "10, 4, 8, 6, -1, 1",
            "8, 6, 10, 4, 1, -1"
    })
    void 궁성_내부_대각선_직선에_대한_방향을_제대로_가져온다(int startX, int startY, int endX, int endY, int dx, int dy) {
        Position start = new Position(startX, startY);
        Position end = new Position(endX, endY);

        Movement movement = start.getLinearDirection(end);

        assertThat(movement.getDx()).isEqualTo(dx);
        assertThat(movement.getDy()).isEqualTo(dy);
    }

    @Test
    void 직선이_아닌_경우_방향을_가져오면_예외가_발생한다() {
        Position start = new Position(1, 1);
        Position end = new Position(2, 3);

        assertThatThrownBy(() -> start.getLinearDirection(end))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("직선이 아닙니다.");
    }

    @ParameterizedTest
    @CsvSource({
            "1,5,2,6",
            "2,4,3,5",
            "9,4,10,5",
            "8,5,9,6"
    })
    void 궁성_내부_이동_가능한_대각선이_아닌_경우_예외가_발생한다(int startX, int startY, int endX, int endY) {
        Position start = new Position(startX, startY);
        Position end = new Position(endX, endY);

        assertThatThrownBy(() -> start.getLinearDirection(end))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("직선이 아닙니다.");
    }

    @Test
    void 직선이_아닌_경우_길이를_가져오면_예외가_발생한다() {
        Position start = new Position(1, 1);
        Position end = new Position(2, 3);

        assertThatThrownBy(() -> start.calculateLinearDistance(end))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("직선이 아닙니다.");
    }

    @ParameterizedTest
    @CsvSource({
            "1,4,1,2,2",
            "5,8,2,8,3",
            "3,2,3,7,5",
            "1,5,2,5,1"
    })
    void 상하좌우_직선에_대한_길이를_제대로_가져온다(int startX, int startY, int endX, int endY, int distance) {
        Position start = new Position(startX, startY);
        Position end = new Position(endX, endY);

        assertThat(start.calculateLinearDistance(end)).isEqualTo(distance);
    }

    @ParameterizedTest
    @CsvSource({
            "8,4,10,6,2",
            "10,4,9,5,1",
            "3,6,2,5,1",
            "3,4,1,6,2"
    })
    void 궁성_내부_대각선에_대한_길이를_제대로_가져온다(int startX, int startY, int endX, int endY, int distance) {
        Position start = new Position(startX, startY);
        Position end = new Position(endX, endY);

        assertThat(start.calculateLinearDistance(end)).isEqualTo(distance);
    }
}

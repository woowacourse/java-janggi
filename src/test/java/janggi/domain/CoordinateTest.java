package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.movestep.MoveStep;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CoordinateTest {

    @Test
    @DisplayName("가로, 세로 위치를 가진 좌표를 생성할 수 있다.")
    void test1() {
        // given

        // when
        Coordinate coordinate = new Coordinate(1, 1);

        // then
        assertThat(coordinate).isEqualTo(new Coordinate(1, 1));
    }

    @Test
    @DisplayName("가로 좌표가 0 이하이면 예외가 발생한다.")
    void test3() {
        // given

        // when & then
        assertThatThrownBy(() -> new Coordinate(0, 1))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("가로 좌표는 1에서 9사이여야 합니다.");
    }

    @Test
    @DisplayName("가로 좌표가 10 이상이면 예외가 발생한다.")
    void test4() {
        // given

        // when & then
        assertThatThrownBy(() -> new Coordinate(10, 1))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("가로 좌표는 1에서 9사이여야 합니다.");
    }

    @Test
    @DisplayName("세로 좌표가 0 이하이면 예외가 발생한다.")
    void test5() {
        // given

        // when & then
        assertThatThrownBy(() -> new Coordinate(1, 0))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("세로 좌표는 1에서 10사이여야 합니다.");
    }

    @Test
    @DisplayName("세로 좌표가 11 이상이면 예외가 발생한다.")
    void test6() {
        // given

        // when & then
        assertThatThrownBy(() -> new Coordinate(1, 11))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("세로 좌표는 1에서 10사이여야 합니다.");
    }

    @Test
    @DisplayName("움직임을 받아 현재 좌표에서 움직일 수 있는 지 알 수 있다.")
    void test7() {
        // given
        Coordinate coordinate = new Coordinate(5, 5);

        // when
        boolean canMove = coordinate.canMove(MoveStep.LEFT);

        // then
        assertThat(canMove).isTrue();
    }

    @Test
    @DisplayName("움직임을 받아 현재 좌표에서 움직인 좌표를 반환한다.")
    void test8() {
        // given
        Coordinate coordinate = new Coordinate(5, 5);

        // when
        Coordinate moved = coordinate.move(MoveStep.LEFT_UP);

        // then
        assertThat(moved).isEqualTo(new Coordinate(4, 4));
    }

    @Test
    @DisplayName("움직임을 받아 현재 좌표에서 움직였을 때 범위를 벗어나면 예외가 발생한다.")
    void test9() {
        // given
        Coordinate coordinate = new Coordinate(1, 1);

        // when
        assertThatThrownBy(() -> coordinate.move(MoveStep.UP))
            .isInstanceOf(IllegalArgumentException.class);
    }
}

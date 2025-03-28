package janggi.domain.piece.movepath;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Fixtures;
import janggi.domain.piece.Direction;
import janggi.domain.piece.Point;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PalaceMovePathTest {

    @DisplayName("출발지와 목적지가 궁상 내부가 아니면 움직일 수 없다.")
    @Test
    void canMove_whenOutPalacePoints() {
        //given
        PalaceMovePath palaceMovePath = new PalaceMovePath(Direction.RIGHT);

        // when
        boolean result = palaceMovePath.canMove(Fixtures.ONE_ONE, Fixtures.ONE_TWO);

        //then
        assertThat(result).isFalse();
    }

    @DisplayName("출발지에서 목적지로 갈 수 없는 방향이라면 움직일 수 없다.")
    @Test
    void cannotMoveDirection() {
        //given
        PalaceMovePath palaceMovePath = new PalaceMovePath(Direction.RIGHT);

        // when
        boolean result = palaceMovePath.canMove(Fixtures.ONE_FOUR, Fixtures.TWO_FOUR);

        //then
        assertThat(result).isFalse();
    }

    @DisplayName("궁상 내부의 대각선을 이동할 수 있는 좌표가 아닌데 대각선으로 가려고 하면 움직일 수 없다.")
    @Test
    void cannotMove_whenCannotMoveDiagonal() {
        //given
        PalaceMovePath palaceMovePath = new PalaceMovePath(Direction.UP_RIGHT_DIAGONAL);

        // when
        boolean result = palaceMovePath.canMove(Fixtures.TWO_FOUR, Fixtures.ONE_FIVE);

        //then
        assertThat(result).isFalse();
    }

    @DisplayName("궁상 내부의 대각선을 이동할 수 있는 좌표일때 대각선으로 가려고 하면 움직일 수 있다.")
    @Test
    void canMove_whenCanMoveDiagonal() {
        //given
        PalaceMovePath palaceMovePath = new PalaceMovePath(Direction.UP_RIGHT_DIAGONAL);

        // when
        boolean result = palaceMovePath.canMove(Fixtures.THREE_FOUR, Fixtures.TWO_FIVE);

        //then
        assertThat(result).isTrue();
    }

    @DisplayName("궁성 내부에서 이동할 수 있는 목적지라면 이동할 수 있다.")
    @Test
    void canMove() {
        //given
        PalaceMovePath palaceMovePath = new PalaceMovePath(Direction.LEFT);

        // when
        boolean result = palaceMovePath.canMove(Fixtures.TWO_FIVE, Fixtures.TWO_FOUR);

        //then
        assertThat(result).isTrue();
    }

    @DisplayName("목적지까지 가는데 거쳐간 좌표를 반환한다.")
    @Test
    void movePoints() {
        //given
        PalaceMovePath palaceMovePath = new PalaceMovePath(Direction.UP_RIGHT_DIAGONAL);

        // when
        List<Point> points = palaceMovePath.movePoints(Fixtures.THREE_FOUR, Fixtures.TWO_FIVE);

        //then
        Assertions.assertThat(points).isEqualTo(List.of(Fixtures.TWO_FIVE));
    }
}
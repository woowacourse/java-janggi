package janggi.domain.piece.movepath;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Fixtures;
import janggi.domain.piece.Direction;
import janggi.domain.piece.Point;
import janggi.domain.piece.palace.ChuPalace;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EndlessPalaceMovePathTest {

    @DisplayName("궁성 내부에서 움직일 수 없는 좌표라면 움직일 수 없다.")
    @Test
    void cannotMoveInPalace() {
        //given
        EndlessPalaceMovePath endlessPalaceMovePath = new EndlessPalaceMovePath(new ChuPalace(), Direction.RIGHT);

        //when
        boolean result = endlessPalaceMovePath.canMove(Fixtures.TEN_SEVEN, Fixtures.THREE_NINE);

        //then
        assertThat(result).isFalse();
    }

    @DisplayName("출발지에서 목적지로 갈 수 없는 방향이라면 움직일 수 없다.")
    @Test
    void cannotMoveDirection() {
        //given
        EndlessPalaceMovePath endlessPalaceMovePath = new EndlessPalaceMovePath(new ChuPalace(), Direction.LEFT);

        //when
        boolean result = endlessPalaceMovePath.canMove(Fixtures.TEN_FIVE, Fixtures.TEN_SIX);

        //then
        assertThat(result).isFalse();
    }

    @DisplayName("목적지까지 가는데 거쳐간 좌표를 반환한다.")
    @Test
    void movePoints() {
        //given
        EndlessPalaceMovePath endlessPalaceMovePath = new EndlessPalaceMovePath(new ChuPalace(),
                Direction.UP_RIGHT_DIAGONAL);

        //when
        List<Point> points = endlessPalaceMovePath.movePoints(Fixtures.TEN_FOUR, Fixtures.EIGHT_SIX);

        //then
        assertThat(points).isEqualTo(List.of(Fixtures.NINE_FIVE, Fixtures.EIGHT_SIX));
    }
}
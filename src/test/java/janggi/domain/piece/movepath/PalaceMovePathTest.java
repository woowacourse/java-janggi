package janggi.domain.piece.movepath;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Fixtures;
import janggi.domain.piece.Direction;
import janggi.domain.piece.Point;
import janggi.domain.piece.palace.HanPalace;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PalaceMovePathTest {

    @DisplayName("출발지에서 목적지로 갈 수 없는 방향이라면 움직일 수 없다.")
    @Test
    void cannotMoveDirection() {
        //given
        PalaceMovePath palaceMovePath = new PalaceMovePath(new HanPalace(), Direction.RIGHT);

        // when
        boolean result = palaceMovePath.canMove(Fixtures.ONE_FOUR, Fixtures.TWO_FOUR);

        //then
        assertThat(result).isFalse();
    }

    @DisplayName("목적지까지 가는데 거쳐간 좌표를 반환한다.")
    @Test
    void movePoints() {
        //given
        PalaceMovePath palaceMovePath = new PalaceMovePath(new HanPalace(), Direction.UP_RIGHT_DIAGONAL);

        // when
        List<Point> points = palaceMovePath.movePoints(Fixtures.THREE_FOUR, Fixtures.TWO_FIVE);

        //then
        Assertions.assertThat(points).isEqualTo(List.of(Fixtures.TWO_FIVE));
    }
}
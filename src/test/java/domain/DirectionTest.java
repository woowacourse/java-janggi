package domain;

import domain.piece.move.Direction;
import domain.piece.move.Vector;
import domain.point.Point;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class DirectionTest {

    @Test
    @DisplayName("주어진 경로를 순차적으로 이동했을 때 최종 목적지에 도달하는지 확인한다.")
    void shouldReachTargetWhenFollowingDirections() {
        Point current = new Point(0, 0);
        Point target = new Point(2, 1);

        Direction directions = new Direction(List.of(Vector.DOWN, Vector.RIGHT_DOWN));

        Assertions.assertThat(directions.canReach(current, target))
                .isTrue();
    }

}

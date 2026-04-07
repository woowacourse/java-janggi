package domain;

import domain.move.Direction;
import domain.move.Directions;
import domain.move.Vector;
import domain.point.Point;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DirectionTest {
    @Test
    @DisplayName("주어진 경로를 순차적으로 이동했을 때 최종 목적지에 도달하는지 확인한다.")
    void should_reach_target_when_following_directions() {
        Point current = new Point(0, 0);
        Point target = new Point(2, 1);

        Direction directions = new Direction(List.of(Vector.DOWN, Vector.RIGHT_DOWN));

        Assertions.assertThat(directions.canReach(current, target))
                .isTrue();
    }

    @Test
    @DisplayName("주어진 경로의 좌표들을 최종적으로 반환하는지 확인한다.")
    void should_return_all_points_along_destination() {
        Direction direction1 = new Direction(List.of(Vector.UP, Vector.LEFT_UP));
        Direction direction2 = new Direction(List.of(Vector.DOWN, Vector.RIGHT_DOWN));
        Point from = new Point(0, 0);
        Point to = new Point(2, 1);

        Directions directions = new Directions(List.of(direction1, direction2));

        List<Point> actual = directions.findPoints(from, to);

        Assertions.assertThat(actual).containsExactly(
                new Point(1, 0),
                new Point(2, 1)
        );
    }
}

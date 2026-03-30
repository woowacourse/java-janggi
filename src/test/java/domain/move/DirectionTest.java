package domain.move;

import domain.intersection.Intersection;
import domain.move.directions.Direction;
import domain.move.directions.Directions;
import domain.move.directions.Vector;
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

    @Test
    @DisplayName("주어진 경로의 좌표들을 최종적으로 반환하는지 확인한다.")
    void returnAllPointsAlongDestination(){
        Direction direction1 = new Direction(List.of(Vector.UP, Vector.LEFT_UP));
        Direction direction2 = new Direction(List.of(Vector.DOWN, Vector.RIGHT_DOWN));
        Point start = new Point(0, 0);
        Point end = new Point(2, 1);

        Intersection origin = Intersection.empty(start);
        Intersection destination = Intersection.empty(end);

        Directions directions = new Directions(List.of(direction1, direction2));

        List<Point> actual = directions.findPoints(origin, destination);

        Assertions.assertThat(actual).containsExactlyInAnyOrder(
                new Point(1, 0),
                new Point(2, 1)
        );
    }


}

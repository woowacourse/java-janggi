package domain.move;

import domain.intersection.Intersection;
import domain.intersection.palace.NormalIntersection;
import domain.move.directions.Direction;
import domain.move.directions.Directions;
import domain.move.directions.Vector;
import domain.piece.Team;
import domain.point.Point;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static domain.move.directions.Vector.DOWN;
import static domain.move.directions.Vector.UP;

class DirectionTest {

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
    void returnAllPointsAlongDestination() {
        Direction direction1 = new Direction(List.of(UP, Vector.LEFT_UP));
        Direction direction2 = new Direction(List.of(Vector.DOWN, Vector.RIGHT_DOWN));
        Point start = new Point(0, 0);
        Point end = new Point(2, 1);

        Intersection origin = NormalIntersection.empty(start);
        Intersection destination = NormalIntersection.empty(end);

        Directions directions = new Directions(List.of(direction1, direction2));

        List<Point> actual = directions.findPoints(origin, destination);

        Assertions.assertThat(actual).containsExactlyInAnyOrder(
                new Point(1, 0),
                new Point(2, 1)
        );
    }

    @Test
    @DisplayName("초의 전진 방향에서 아랫방향은 제거한다.")
    void removeBackWordDirectionOfCho() {
        // given
        Direction allVector = new Direction(List.of(
                UP, Vector.DOWN, Vector.LEFT, Vector.RIGHT,
                Vector.LEFT_UP, Vector.LEFT_DOWN, Vector.RIGHT_UP, Vector.RIGHT_DOWN
        ));

        Direction expected = new Direction(List.of(
                UP, Vector.LEFT, Vector.RIGHT,
                Vector.LEFT_UP, Vector.RIGHT_UP
        ));

        // when
        Direction forward = allVector.toForward(Team.CHO);

        // then
        Assertions.assertThat(forward)
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("한의 전진 방향에서 윗방향은 제거한다.")
    void removeForwardDirectionOfHan() {
        // given
        Direction allVector = new Direction(List.of(
                UP, Vector.DOWN, Vector.LEFT, Vector.RIGHT,
                Vector.LEFT_UP, Vector.LEFT_DOWN, Vector.RIGHT_UP, Vector.RIGHT_DOWN
        ));

        Direction expected = new Direction(List.of(
                Vector.DOWN, Vector.LEFT, Vector.RIGHT,
                Vector.LEFT_DOWN, Vector.RIGHT_DOWN
        ));

        // when
        Direction forward = allVector.toForward(Team.HAN);

        // then
        Assertions.assertThat(forward)
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("이동 방향은 누적하여 생성할 수 있다.")
    void directionsCanGenerateByCumulative() {
        // given
        Directions expected = new Directions(List.of(
                new Direction(List.of(UP)),
                new Direction(List.of(UP, UP)),
                new Direction(List.of(UP, UP, UP)),
                new Direction(List.of(UP, UP, UP, UP)),
                new Direction(List.of(UP, UP, UP, UP, UP)),
                new Direction(List.of(UP, UP, UP, UP, UP, UP)),
                new Direction(List.of(UP, UP, UP, UP, UP, UP, UP)),
                new Direction(List.of(UP, UP, UP, UP, UP, UP, UP, UP)),
                new Direction(List.of(UP, UP, UP, UP, UP, UP, UP, UP, UP))
        ));

        // when
        Directions actual = Directions.cumulative(UP, 9);

        // then
        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("이동 방향에 새로운 이동 방향을 추가할 수 있다.")
    void directionsCanAddAdditionalDirections() {
        // given
        Directions expected = new Directions(List.of(
                new Direction(List.of(UP)),
                new Direction(List.of(UP, UP)),
                new Direction(List.of(DOWN)),
                new Direction(List.of(DOWN, DOWN))
        ));

        Directions upDirections = Directions.cumulative(UP, 2);
        Directions downDirections = Directions.cumulative(DOWN, 2);

        // when
        Directions actual = upDirections.add(downDirections);

        // then
        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("주어진 이동 방향의 최대 칸수보다 길면 제외할 수 있다.")
    void directionsCanLimitMaxDistance() {
        Directions expected = new Directions(List.of(
                new Direction(List.of(UP)),
                new Direction(List.of(DOWN))
        ));

        // when
        Directions actual = new Directions(List.of(
                new Direction(List.of(UP)),
                new Direction(List.of(UP, UP)),
                new Direction(List.of(DOWN)),
                new Direction(List.of(DOWN, DOWN))
        )).limitDistance(1);

        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

}

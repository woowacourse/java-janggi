package domain.movements;

import domain.Point;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class DefaultMovementTest {
    @Nested
    @DisplayName("전체 경로를 계산할 때")
    class TestCalculateRoute {
        @Test
        @DisplayName("이동 가능한 도착지점들을 반환한다")
        void test_calculateTotalArrivalPoints() {
            // given
            List<Direction> directions1 = List.of(Direction.NORTH, Direction.NORTHWEST, Direction.NORTHWEST);
            List<Direction> directions2 = List.of(Direction.SOUTH, Direction.SOUTHWEST, Direction.SOUTHWEST);
            List<Route> routes = List.of(new Route(directions1), new Route(directions2));
            DefaultMovement defaultMovement = new DefaultMovement(routes);
            Point startPoint = new Point(0, 0);

            // when
            List<Point> arrivalPoints = defaultMovement.calculateTotalArrivalPoints(startPoint);

            // then
            assertThat(arrivalPoints).contains(new Point(3, -2), new Point(-3, -2));
        }

        @Test
        @DisplayName("이동 경로 지점들을 반환한다.")
        void test_calculateRoutePoints() {
            // given
            List<Direction> directions1 = List.of(Direction.NORTH, Direction.NORTHWEST, Direction.NORTHWEST);
            List<Route> routes = List.of(new Route(directions1));
            DefaultMovement defaultMovement = new DefaultMovement(routes);
            Point startPoint = new Point(0, 0);
            Point arrivalPoint = new Point(3, -2);

            // when
            List<Point> arrivalPoints = defaultMovement.calculateRoutePoints(startPoint, arrivalPoint);

            // then
            assertThat(arrivalPoints).contains(new Point(1, 0), new Point(2, -1), new Point(3, -2));
        }
    }
}

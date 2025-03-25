package domain.movements;

import domain.board.Point;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class DefaultMovementTest {
    @Nested
    @DisplayName("전체 경로를 계산할 때")
    class TestCalculateRoute {
        @Test
        @DisplayName("이동 가능한 도착점들을 반환한다")
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
        @DisplayName("이동 가능하지 않은 도착점들은 반환하지 않는다")
        void test_doesNotReturnInvalidPoints() {
            // given
            List<Direction> directions1 = List.of(Direction.NORTH, Direction.NORTHWEST, Direction.NORTHWEST);
            List<Direction> directions2 = List.of(Direction.SOUTH, Direction.SOUTHWEST, Direction.SOUTHWEST);
            List<Route> routes = List.of(new Route(directions1), new Route(directions2));
            DefaultMovement defaultMovement = new DefaultMovement(routes);
            Point startPoint = new Point(0, 0);

            Point moveOnceToNorth = new Point(1, 0);
            Point moveOnceToSouth = new Point(-1, 0);

            // when
            List<Point> arrivalPoints = defaultMovement.calculateTotalArrivalPoints(startPoint);

            // then
            assertThat(arrivalPoints).doesNotContain(moveOnceToNorth, moveOnceToSouth);
        }

        @Test
        @DisplayName("존재하지 않는 경로로 이동하려 할 때 예외가 발생한다")
        void test_throwsExceptionForInvalidRoute() {
            // given
            List<Direction> directions1 = List.of(Direction.NORTH, Direction.NORTHWEST, Direction.NORTHWEST);
            List<Direction> directions2 = List.of(Direction.SOUTH, Direction.SOUTHWEST, Direction.SOUTHWEST);
            List<Route> routes = List.of(new Route(directions1), new Route(directions2));
            DefaultMovement defaultMovement = new DefaultMovement(routes);
            Point startPoint = new Point(0, 0);
            Point invalidArrivalPoint = new Point(3, 2);

            // when & then
            assertThatThrownBy(() -> defaultMovement.calculateRoutePoints(startPoint, invalidArrivalPoint))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("해당 도착점으로 도착할 수 없는 기물입니다.");
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

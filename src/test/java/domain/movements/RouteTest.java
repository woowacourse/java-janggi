package domain.movements;

import domain.board.BoardPoint;
import domain.board.Point;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public final class RouteTest {
    @Nested
    @DisplayName("경로를 계산할때")
    class TestCalculateRoute {
        @Test
        @DisplayName("출발점으로부터 도착점의 Point를 계산한다.")
        void test_navigateArrivalPoint() {
            //given
            final List<Direction> givenDirections = List.of(Direction.NORTH, Direction.NORTHEAST, Direction.NORTHEAST);
            final List<Direction> directions = new ArrayList<>(givenDirections);
            final Route route = new Route(directions);
            final BoardPoint startBoardPoint = new BoardPoint(0, 0);
            final Point expectedPoint = new Point(3, 2);

            //when
            final Point actual = route.navigateArrivalPoint(startBoardPoint);

            //then
            assertThat(actual).isEqualTo(expectedPoint);
        }
    }

    @Nested
    @DisplayName("도착 가능 여부를 확인할 때")
    class TestCanArrive {
        @Test
        @DisplayName("도착할 수 있는 지점에 대해 true 를 반환한다")
        void test_canArriveReturnTrue() {
            // given
            Route route = new Route(List.of(Direction.NORTH, Direction.NORTHEAST));
            BoardPoint startBoardPoint = new BoardPoint(0, 0);
            BoardPoint arrivalBoardPoint = new BoardPoint(2, 1);

            // when
            boolean actual = route.canArrive(startBoardPoint, arrivalBoardPoint);

            // then
            assertThat(actual).isTrue();
        }

        @Test
        @DisplayName("도착할 수 없는 지점에 대해 false 를 반환한다")
        void test_canArriveReturnFalse() {
            // given
            Route route = new Route(List.of(Direction.NORTH, Direction.NORTHEAST));
            BoardPoint startBoardPoint = new BoardPoint(0, 0);
            BoardPoint arrivalBoardPoint = new BoardPoint(2, 2);

            // when
            boolean actual = route.canArrive(startBoardPoint, arrivalBoardPoint);

            // then
            assertThat(actual).isFalse();
        }
    }

    @Nested
    @DisplayName("경로를 반환할 때")
    class TestGetAllPointsOnRoute {
        @Test
        void test_returnValidPointsOnRoute() {
            // given
            Route route = new Route(List.of(Direction.NORTH, Direction.NORTHEAST, Direction.NORTHEAST));
            BoardPoint startBoardPoint = new BoardPoint(0, 0);

            // when
            List<BoardPoint> actual = route.getAllPointsOnRoute(startBoardPoint);

            // then
            assertAll(
                    () -> assertThat(actual).hasSize(3),
                    () -> assertThat(actual).containsExactlyInAnyOrder(
                            new BoardPoint(1, 0),
                            new BoardPoint(2, 1),
                            new BoardPoint(3, 2))
            );
        }

        @Test
        @DisplayName("시작점은 포함하지 않는다")
        void test_doesNotContainStartPoint() {
            // given
            Route route = new Route(List.of(Direction.NORTH, Direction.NORTHEAST));
            BoardPoint startBoardPoint = new BoardPoint(0, 0);

            // when & then
            assertThat(route.getAllPointsOnRoute(startBoardPoint)).doesNotContain(startBoardPoint);
        }

        @Test
        @DisplayName("경로가 아닌 지점은 포함하지 않는다")
        void test_doesNotContainInvalidPoint() {
            // given
            Route route = new Route(List.of(Direction.NORTH, Direction.NORTHEAST));
            BoardPoint startBoardPoint = new BoardPoint(1, 0);
            BoardPoint boardPointToSouth = new BoardPoint(0, 0);

            // when & then
            assertThat(route.getAllPointsOnRoute(startBoardPoint)).doesNotContain(boardPointToSouth);
        }
    }
}

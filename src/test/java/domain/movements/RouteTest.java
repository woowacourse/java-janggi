package domain.movements;

import domain.Point;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
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
            final Point startPoint = new Point(0, 0);
            final Point expectedPoint = new Point(3, 2);

            //when
            final Point actual = route.navigateArrivalPoint(startPoint);

            //then
            Assertions.assertThat(actual).isEqualTo(expectedPoint);
        }
    }

}

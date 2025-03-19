package domain.piece;

import domain.Point;
import domain.Team;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ElephantTest {
    @Test
    @DisplayName("피스가 이동할 수 있는 지점들을 전부 반환한다")
    void test_getArrivalPoint() {
        // given
        Elephant elephant = new Elephant(Team.CHO);
        Point startPoint = new Point(0, 0);

        // when
        List<Point> actual = elephant.getArrivalPoint(startPoint);

        // then
        assertThat(actual).containsExactlyInAnyOrder(
                new Point(3, 2),
                new Point(3, -2),
                new Point(2, 3),
                new Point(-2, 3),
                new Point(-3, 2),
                new Point(-3, -2),
                new Point(2, -3),
                new Point(-2, -3)
        );
    }

    @Test
    @DisplayName("경로에 있는 모든 지점들을 반환한다")
    void test_getRoutePoints() {
        // given
        Elephant elephant = new Elephant(Team.CHO);
        Point startPoint = new Point(0, 0);
        Point arrivalPoint = new Point(3, 2);

        // when
        List<Point> routePoints = elephant.getRoutePoints(startPoint, arrivalPoint);

        // then
        assertThat(routePoints).containsExactlyInAnyOrder(
                new Point(1, 0),
                new Point(2, 1),
                new Point(3, 2)
        );
    }
}

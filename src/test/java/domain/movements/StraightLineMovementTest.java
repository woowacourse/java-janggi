package domain.movements;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Point;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public final class StraightLineMovementTest {

    @Test
    @DisplayName("도착할 수 있는 지점들을 모두 반환한다")
    void test_searchTotalArrivalPoints() {
        // given
        final StraightLineMovement straightLineMovement = new StraightLineMovement();

        // when
        final List<Point> points = straightLineMovement.searchTotalArrivalPoints(new Point(3, 3));

        // then
        assertThat(points).hasSize(40);
    }

    @Test
    @DisplayName("경로 상의 모든 지점들을 반환한다")
    void test_calculatePointsOnRoute() {
        // given
        final StraightLineMovement straightLineMovement = new StraightLineMovement();

        // when
        final Point start = new Point(3, 3);
        final Point arrival = new Point(3, 6);
        final List<Point> points = straightLineMovement.calculatePointsOnRoute(start, arrival);

        // then
        assertThat(points).containsExactlyInAnyOrder(
                new Point(3, 4),
                new Point(3, 5),
                arrival
        );
    }
}

package domain.movements;

import domain.Point;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EndlessMovementTest {
    @Test
    @DisplayName("도착할 수 있는 지점들을 모두 반환한다")
    void test_calculateTotalArrivalPoints() {
        // given
        EndlessMovement endlessMovement = new EndlessMovement();

        // when
        List<Point> points = endlessMovement.calculateTotalArrivalPoints(new Point(3, 3));

        // then
        assertThat(points).hasSize(40);
    }

    @Test
    @DisplayName("경로 상의 모든 지점들을 반환한다")
    void test_calculateRoutePoints() {
        // given
        EndlessMovement endlessMovement = new EndlessMovement();

        // when
        List<Point> points = endlessMovement.calculateRoutePoints(new Point(3, 3), new Point(3, 6));

        // then
        assertThat(points).containsExactlyInAnyOrder(
                new Point(3, 4),
                new Point(3, 5),
                new Point(3, 6)
        );
    }
}

package domain.movements;

import domain.board.Point;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
        assertThat(points).containsExactlyInAnyOrder(
                new Point(5, 3),
                new Point(6, 3),
                new Point(7, 3),
                new Point(8, 3),
                new Point(9, 3),
                new Point(3, 4),
                new Point(3, 5),
                new Point(3, 6),
                new Point(3, 7),
                new Point(3, 8),
                new Point(0, 3),
                new Point(3, 2),
                new Point(3, 1),
                new Point(3, 0),
                new Point(4, 3),
                new Point(2, 3),
                new Point(1, 3)
        );
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

    @Test
    @DisplayName("이동할 수 없는 위치로 경로를 반환할 것을 요청하는 경우 예외를 던진다")
    void test_calculateRoutePointsThrowsException() {
        // given
        EndlessMovement endlessMovement = new EndlessMovement();

        Point startPoint = new Point(0, 0);
        Point invalidArrivalPoint = new Point(1, 1);

        // when & then
        assertThatThrownBy(() -> endlessMovement.calculateRoutePoints(startPoint, invalidArrivalPoint))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 위치로 이동할 수 없습니다.");
    }
}

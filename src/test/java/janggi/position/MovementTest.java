package janggi.position;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import janggi.domain.position.Movement;
import janggi.domain.position.Position;
import janggi.exception.ErrorException;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MovementTest {

    @DisplayName("출발 위치와 도착 위치가 같은 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenNoMovement() {
        // given
        Position origin = Position.of(1, 1);
        Position target = Position.of(1, 1);

        // when & then
        assertThatCode(() -> new Movement(origin, target))
                .isInstanceOf(ErrorException.class)
                .hasMessageContaining("출발 위치와 도착 위치가 같은 경우 움직일 수 없습니다.");
    }

    @DisplayName("출발 위치와 도착 위치가 같은 수평선상에 있는지 확인한다.")
    @Test
    void horizontalTest() {
        // given
        Position origin = Position.of(0, 0);
        Position target = Position.of(2, 0);
        Movement movement = new Movement(origin, target);

        // when & then
        assertThat(movement.isHorizontal())
                .isTrue();

    }

    @DisplayName("출발 위치와 도착 위치가 같은 수직선상에 있는지 확인한다.")
    @Test
    void verticalTest() {
        // given
        Position origin = Position.of(0, 0);
        Position target = Position.of(0, 2);
        Movement movement = new Movement(origin, target);

        // when & then
        assertThat(movement.isVertical())
                .isTrue();

    }

    @DisplayName("출발 위치와 도착 위치의 수평선상 거리를 계산한다.")
    @Test
    void horizontalDistanceTest() {
        // given
        Position origin = Position.of(0, 0);
        Position target = Position.of(2, 0);
        Movement movement = new Movement(origin, target);

        // when & then
        assertThat(movement.calculateXDistance())
                .isEqualTo(2);

    }

    @DisplayName("출발 위치와 도착 위치의 수직선상 거리를 계산한다.")
    @Test
    void verticalDistanceTest() {
        // given
        Position origin = Position.of(0, 0);
        Position target = Position.of(0, 2);
        Movement movement = new Movement(origin, target);

        // when & then
        assertThat(movement.calculateYDistance())
                .isEqualTo(2);

    }

    @DisplayName("출발 위치와 도착 위치의 수평선상 경로를 계산한다.")
    @Test
    void horizontalRouteTest() {
        // given
        Position origin = Position.of(0, 0);
        Position target = Position.of(3, 0);
        Movement movement = new Movement(origin, target);

        Set<Position> route = Set.of(
                Position.of(1, 0),
                Position.of(2, 0)
        );

        // when
        Set<Position> horizontalRoute = movement.findRoute();

        // then
        assertThat(route.containsAll(horizontalRoute) && horizontalRoute.containsAll(route))
                .isTrue();

    }

    @DisplayName("출발 위치와 도착 위치의 수직선상 경로를 계산한다.")
    @Test
    void verticalRouteTest() {
        // given
        Position origin = Position.of(0, 0);
        Position target = Position.of(0, 3);
        Movement movement = new Movement(origin, target);

        Set<Position> route = Set.of(
                Position.of(0, 1),
                Position.of(0, 2)
        );

        // when
        Set<Position> verticalRoute = movement.findRoute();

        // then
        assertThat(route.containsAll(verticalRoute) && verticalRoute.containsAll(route))
                .isTrue();

    }
}

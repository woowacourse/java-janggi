package movement;

import domain.movement.MovePath;
import domain.movement.MovePaths;
import domain.movement.Movement;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import domain.position.Position;

import java.util.Set;

class MovePathsTest {

    @Test
    @DisplayName("경로들이 모두 같은 최단 거리일 경우 최단 거리를 반환한다.")
    void calculateDistanceSuccess() {
        // given
        final MovePaths movePaths = new MovePaths(Set.of(
                new MovePath(Movement.UP, Movement.LEFT_UP, Movement.LEFT_UP),
                new MovePath(Movement.UP, Movement.RIGHT_UP, Movement.RIGHT_UP),
                new MovePath(Movement.DOWN, Movement.LEFT_DOWN, Movement.LEFT_DOWN),
                new MovePath(Movement.DOWN, Movement.RIGHT_DOWN, Movement.RIGHT_DOWN),
                new MovePath(Movement.LEFT, Movement.LEFT_UP, Movement.LEFT_UP),
                new MovePath(Movement.LEFT, Movement.LEFT_DOWN, Movement.LEFT_DOWN),
                new MovePath(Movement.RIGHT, Movement.RIGHT_UP, Movement.RIGHT_UP),
                new MovePath(Movement.RIGHT, Movement.RIGHT_DOWN, Movement.RIGHT_DOWN)
        ));
        double expected = Math.sqrt(13);

        // when
        double actual = movePaths.calculateDistance();

        // then
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("경로들이 모두 같은 최단 거리가 아닐 경우 예외를 발생시킨다.")
    void calculateDistanceFailure() {
        // given
        final MovePaths movePaths = new MovePaths(Set.of(
                new MovePath(Movement.UP, Movement.LEFT_UP, Movement.LEFT_UP),
                new MovePath(Movement.UP, Movement.RIGHT_UP, Movement.UP)
        ));

        // when
        // then
        Assertions.assertThatThrownBy(
                movePaths::calculateDistance
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("여러 경로들 중 도착지에 맞는 경로 찾아 반환")
    void findCorrectMovePathSuccess() {
        // given
        final MovePaths movePaths = new MovePaths(Set.of(
                new MovePath(Movement.UP, Movement.LEFT_UP, Movement.LEFT_UP),
                new MovePath(Movement.UP, Movement.RIGHT_UP, Movement.RIGHT_UP),
                new MovePath(Movement.DOWN, Movement.LEFT_DOWN, Movement.LEFT_DOWN),
                new MovePath(Movement.DOWN, Movement.RIGHT_DOWN, Movement.RIGHT_DOWN),
                new MovePath(Movement.LEFT, Movement.LEFT_UP, Movement.LEFT_UP),
                new MovePath(Movement.LEFT, Movement.LEFT_DOWN, Movement.LEFT_DOWN),
                new MovePath(Movement.RIGHT, Movement.RIGHT_UP, Movement.RIGHT_UP),
                new MovePath(Movement.RIGHT, Movement.RIGHT_DOWN, Movement.RIGHT_DOWN)
        ));
        Position src = new Position(1, 1);
        Position dest = new Position(3, 4);
        MovePath expected = new MovePath(Movement.DOWN, Movement.RIGHT_DOWN, Movement.RIGHT_DOWN);

        // when
        MovePath actual = movePaths.findCorrectMovePath(src, dest);

        // then
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("여러 경로들 중 도착지에 맞는 경로를 찾지 못했을 경우 예외 처리")
    void findCorrectMovePathFailure() {
        // given
        final MovePaths movePaths = new MovePaths(Set.of(
                new MovePath(Movement.UP, Movement.LEFT_UP, Movement.LEFT_UP),
                new MovePath(Movement.UP, Movement.RIGHT_UP, Movement.RIGHT_UP),
                new MovePath(Movement.DOWN, Movement.LEFT_DOWN, Movement.LEFT_DOWN),
                new MovePath(Movement.DOWN, Movement.RIGHT_DOWN, Movement.RIGHT_DOWN),
                new MovePath(Movement.LEFT, Movement.LEFT_UP, Movement.LEFT_UP),
                new MovePath(Movement.LEFT, Movement.LEFT_DOWN, Movement.LEFT_DOWN),
                new MovePath(Movement.RIGHT, Movement.RIGHT_UP, Movement.RIGHT_UP),
                new MovePath(Movement.RIGHT, Movement.RIGHT_DOWN, Movement.RIGHT_DOWN)
        ));
        Position src = new Position(1, 1);
        Position dest = new Position(5, 5);

        // when
        // then
        Assertions.assertThatThrownBy(
                () -> movePaths.findCorrectMovePath(src, dest)
        ).isInstanceOf(IllegalArgumentException.class);
    }
}

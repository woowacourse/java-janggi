package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MovementTest {

    @Test
    @DisplayName("최종 목적지의 좌표를 반환한다")
    void canMovePositionTest() {
        List<Direction> directions = List.of(Direction.UP, Direction.RIGHT_UP, Direction.RIGHT);
        Movement movement = new Movement(directions);
        Position startPosition = Position.of(0, 0);
        Position endPosition = Position.of(2, 2);

        boolean result = movement.isValidMove(startPosition, endPosition);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("최종 목적지의 좌표를 반환한다")
    void canMovePositionTest2() {
        List<Direction> directions = List.of(Direction.DOWN, Direction.RIGHT_UP, Direction.RIGHT);
        Movement movement = new Movement(directions);
        Position startPosition = Position.of(0, 0);
        Position endPosition = Position.of(1, 2);

        boolean result = movement.isValidMove(startPosition, endPosition);

        assertThat(result).isFalse();
    }
}

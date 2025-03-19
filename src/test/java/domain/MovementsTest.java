package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MovementsTest {

    @Test
    @DisplayName("도착 위치로 이동할 수 있으면 true를 반환한다")
    void canMoveFromToTrue() {
        List<Movement> movementList = List.of(
                new Movement(List.of(Direction.UP, Direction.RIGHT_UP, Direction.RIGHT)),
                new Movement(List.of(Direction.LEFT, Direction.LEFT_DOWN, Direction.DOWN))
        );
        Movements movements = new Movements(movementList);

        Position startPosition = Position.of(2, 2);
        Position endPosition = Position.of(0, 0);
        boolean result = movements.canMoveFromTo(startPosition, endPosition);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("도착 위치로 이동할 수 없으면 false를 반환한다")
    void canMoveFromToFalse() {
        List<Movement> movementList = List.of(
                new Movement(List.of(Direction.UP, Direction.RIGHT_UP, Direction.RIGHT)),
                new Movement(List.of(Direction.LEFT, Direction.LEFT_DOWN, Direction.DOWN))
        );
        Movements movements = new Movements(movementList);

        Position startPosition = Position.of(2, 2);
        Position endPosition = Position.of(8, 8);
        boolean result = movements.canMoveFromTo(startPosition, endPosition);

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("이동 경로의 포지션들을 반환한다")
    void findIntermediatePositionsTest() {
        List<Movement> movementList = List.of(
                new Movement(List.of(Direction.UP, Direction.RIGHT_UP, Direction.RIGHT)),
                new Movement(List.of(Direction.LEFT, Direction.LEFT_DOWN, Direction.DOWN))
        );
        Movements movements = new Movements(movementList);

        Position startPosition = Position.of(2, 2);
        Position endPosition = Position.of(0, 0);

        assertThat(movements.findIntermediatePositions(startPosition, endPosition))
                .containsExactly(Position.of(2, 1), Position.of(1, 0));
    }

    @Test
    @DisplayName("이동 경로로 이동할 수 없으면 예외가 발생한다")
    void findIntermediatePositionsException() {
        List<Movement> movementList = List.of(
                new Movement(List.of(Direction.UP, Direction.RIGHT_UP, Direction.RIGHT)),
                new Movement(List.of(Direction.LEFT, Direction.LEFT_DOWN, Direction.DOWN))
        );
        Movements movements = new Movements(movementList);

        Position startPosition = Position.of(2, 2);
        Position endPosition = Position.of(8, 8);

        assertThatThrownBy(() -> movements.findIntermediatePositions(startPosition, endPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("지정한 포지션으로 이동할 수 없습니다.");
    }

}

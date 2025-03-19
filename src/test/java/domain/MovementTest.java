package domain;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.assertj.core.api.Assertions;
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

    @Test
    @DisplayName("중간 지점을 반환한다.")
    void getMiddlePosition(){
        List<Direction> directions = List.of(Direction.UP, Direction.RIGHT_UP, Direction.RIGHT);
        Movement movement = new Movement(directions);
        Position startPosition = Position.of(0, 0);
        Position endPosition = Position.of(2, 2);

        List<Position> positions =  movement.findIntermediatePositions(startPosition,endPosition);

        assertThat(positions).containsExactly(Position.of(1,0),Position.of(2,1));
    }

    @Test
    @DisplayName("중지점 반환 메서드에서 가지 못하면 예외를 반환한다.")
    void getMiddlePositionException(){
        List<Direction> directions = List.of(Direction.UP, Direction.RIGHT_UP, Direction.RIGHT);
        Movement movement = new Movement(directions);
        Position startPosition = Position.of(0, 0);
        Position endPosition = Position.of(2, 3);

        assertThatThrownBy(()->movement.findIntermediatePositions(startPosition,endPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("지정한 포지션으로 이동할 수 없습니다.");
    }
}

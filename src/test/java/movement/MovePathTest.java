package movement;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import position.Position;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MovePathTest {

    @Test
    @DisplayName("이동 경로의 최단 경로의 길이를 구할 수 있다.")
    void calculateDistance() {
        // given
        MovePath movePath = new MovePath(Movement.UP, Movement.LEFT_UP, Movement.LEFT_UP);
        double expected = Math.sqrt(13);

        // when
        double actual = movePath.calculateDistance();

        // then
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("경로 내부 경로가 잘 찾아와 지는지")
    void getInternalMovements() {
        // given
        MovePath movePath = new MovePath(Movement.UP, Movement.LEFT_UP, Movement.LEFT_UP);
        List<MovePath> expected = List.of(
                new MovePath(Movement.UP),
                new MovePath(Movement.UP, Movement.LEFT_UP)
        );

        // when
        MovePaths result = movePath.getInternalMovements();

        // then
        Assertions.assertThat(result.getMovePaths()).containsAll(expected);
    }

    @Test
    @DisplayName("지정된 움직임대로 이동했을 때 목적지에 도달하는지")
    void canReachDestination() {
        // given
        MovePath movePath = new MovePath(Movement.UP, Movement.LEFT_UP, Movement.LEFT_UP);
        Position src = new Position(4, 4);
        Position canReachDest = new Position(2, 1);
        Position canNotReachDest = new Position(3, 6);

        // when
        boolean ableResult = movePath.canReachDestination(src, canReachDest);
        boolean notAbleResult = movePath.canReachDestination(src, canNotReachDest);

        // then
        assertAll(
                () -> Assertions.assertThat(ableResult).isTrue(),
                () -> Assertions.assertThat(notAbleResult).isFalse()
        );
    }
}

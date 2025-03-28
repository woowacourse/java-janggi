package domain.piece.path;

import static fixtures.PositionFixture.*;
import static fixtures.PositionFixture.D3;
import static fixtures.PositionFixture.H0;
import static org.assertj.core.api.Assertions.*;

import domain.position.Direction;
import domain.position.Position;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FixedMultiStepPathFinderTest {

    @Test
    @DisplayName("지나온 경로 반환 테스트")
    void findIntermediatePositionsTest() {
        List<Movement> movements = List.of(
                new Movement(List.of(Direction.UP,Direction.UP,Direction.LEFT_UP))
        );
        FixedMultiStepPathFinder pathFinder = new FixedMultiStepPathFinder(movements);

        List<Position> intermediatePositions = pathFinder.findIntermediatePositions(E0,
                D3);

        assertThat(intermediatePositions).containsExactly(E1, E2);
    }

    @Test
    @DisplayName("갈 수 없는 곳에 이동을 검증하면 예외가 발생한다.")
    void findIntermediatePositionsException() {
        List<Movement> movements = List.of(
                new Movement(List.of(Direction.UP,Direction.UP,Direction.LEFT_UP))
        );
        FixedMultiStepPathFinder pathFinder = new FixedMultiStepPathFinder(movements);

        assertThatThrownBy(()->pathFinder.findIntermediatePositions(D3,
                        H0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 좌표로 이동시킬 수 없습니다.");
    }

}
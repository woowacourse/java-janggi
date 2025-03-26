package domain.piece.path;

import static fixtures.PositionFixture.D3;
import static fixtures.PositionFixture.D4;
import static fixtures.PositionFixture.D5;
import static fixtures.PositionFixture.D6;
import static fixtures.PositionFixture.H0;

import domain.position.Direction;
import domain.position.Position;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DynamicPatternPathFinderTest {

    @Test
    @DisplayName("지나온 경로 반환 테스트")
    void findIntermediatePositionsTest() {
        List<Direction> directions = List.of(Direction.RIGHT, Direction.UP, Direction.DOWN, Direction.LEFT);
        DynamicPatternPathFinder pathFinder = new DynamicPatternPathFinder(directions);

        List<Position> intermediatePositions = pathFinder.findIntermediatePositions(D3,
                D6);

        Assertions.assertThat(intermediatePositions).containsExactly(D4, D5);
    }

    @Test
    @DisplayName("갈 수 없는 곳에 이동을 검증하면 예외가 발생한다.")
    void findIntermediatePositionsException() {
        List<Direction> directions = List.of(Direction.RIGHT, Direction.UP, Direction.DOWN, Direction.LEFT);
        DynamicPatternPathFinder pathFinder = new DynamicPatternPathFinder(directions);

        Assertions.assertThatThrownBy(()->pathFinder.findIntermediatePositions(D3,
                H0))
                        .isInstanceOf(IllegalArgumentException.class)
                                .hasMessage("해당 좌표로 이동시킬 수 없습니다.");

    }
}
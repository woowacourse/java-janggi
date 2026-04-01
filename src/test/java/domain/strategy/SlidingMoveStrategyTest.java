package domain.strategy;

import domain.coordinate.Direction;
import domain.coordinate.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class SlidingMoveStrategyTest {

    @Test
    @DisplayName("직선으로 끝까지 이동한다.")
    void moveStrategyTest() {
        // given
        Position start = new Position(4, 4);
        MoveStrategy strategy = new SlidingMoveStrategy(
                List.of(
                Direction.UP,
                Direction.DOWN,
                Direction.LEFT,
                Direction.RIGHT
        ));

        // when
        List<List<Direction>> result = strategy.calculatePotentialPaths(start);

        // then
        assertThat(result).containsOnly(
                List.of(Direction.LEFT, Direction.LEFT, Direction.LEFT, Direction.LEFT),
                List.of(Direction.RIGHT, Direction.RIGHT, Direction.RIGHT, Direction.RIGHT),
                List.of(Direction.UP, Direction.UP, Direction.UP, Direction.UP),
                List.of(Direction.DOWN, Direction.DOWN, Direction.DOWN, Direction.DOWN, Direction.DOWN)
        );
    }
}

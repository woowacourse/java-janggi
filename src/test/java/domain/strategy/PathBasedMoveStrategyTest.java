package domain.strategy;

import domain.coordinate.Direction;
import domain.coordinate.Position;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class PathBasedMoveStrategyTest {

    @Test
    @DisplayName("경로에 따라 정확한 목적지를 생성한다.")
    void moveStrategyTest() {
        // given
        Position start = new Position(4, 4);

        List<List<Direction>> paths = List.of(
                List.of(Direction.UP, Direction.UP), // (4,4) -> (3,4) -> (2,4)
                List.of(Direction.LEFT, Direction.LEFT) // (4,4) -> (4,3) -> (4,2)
        );

        MoveStrategy strategy = new PathBasedMoveStrategy(paths);

        // when
        List<List<Direction>> result = strategy.calculatePotentialPaths(start);

        // then
        assertThat(result).containsOnly(
                List.of(Direction.UP, Direction.UP),
                List.of(Direction.LEFT, Direction.LEFT)
        );
    }
}

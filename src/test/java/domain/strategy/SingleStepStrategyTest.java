package domain.strategy;

import domain.coordinate.Direction;
import domain.coordinate.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class SingleStepStrategyTest {

    @Test
    @DisplayName("직선으로 입력된 방향으로 한 칸 이동할 수 있다.")
    void moveStrategyTest() {
        // given
        Position start = new Position(4, 4);
        MoveStrategy strategy = new SingleStepStrategy(
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
                List.of(Direction.LEFT),
                List.of(Direction.RIGHT),
                List.of(Direction.UP),
                List.of(Direction.DOWN)
        );
    }

    @Test
    @DisplayName("범위를 넘어갈 시 그 방향은 이동할 수 없다.")
    void moveStrategyRangeTest() {
        // given
        Position start = new Position(0, 0);
        MoveStrategy strategy = new SingleStepStrategy(
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
                List.of(Direction.RIGHT),
                List.of(Direction.DOWN)
        );
    }
}
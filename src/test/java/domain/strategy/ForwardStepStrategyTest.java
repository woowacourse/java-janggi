package domain.strategy;

import domain.board.Side;
import domain.coordinate.Direction;
import domain.coordinate.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class ForwardStepStrategyTest {

    @Test
    @DisplayName("한나라 진영에서는 하/좌/우 한칸씩 이동 가능하다.")
    void moveStrategy_HANSide_Test() {
        // given
        MoveStrategy strategy = new ForwardStepStrategy(List.of(
                Direction.getForward(Side.HAN),
                Direction.LEFT,
                Direction.RIGHT)
        );
        Position start = new Position(3, 4);

        // when
        List<List<Direction>> result = strategy.calculatePotentialPaths(start);

        // then
        assertThat(result).containsOnly(
                List.of(Direction.RIGHT),
                List.of(Direction.LEFT),
                List.of(Direction.DOWN)
        );
    }

    @Test
    @DisplayName("한나라 진영에서는 하/좌/우 한칸씩 이동 가능하다.")
    void moveStrategy_CHUSide_Test() {
        // given
        MoveStrategy strategy = new ForwardStepStrategy(List.of(
                Direction.getForward(Side.CHU),
                Direction.LEFT,
                Direction.RIGHT)
        );
        Position start = new Position(3, 4);

        // when
        List<List<Direction>> result = strategy.calculatePotentialPaths(start);

        // then
        assertThat(result).containsOnly(
                List.of(Direction.RIGHT),
                List.of(Direction.LEFT),
                List.of(Direction.UP)
        );
    }
}

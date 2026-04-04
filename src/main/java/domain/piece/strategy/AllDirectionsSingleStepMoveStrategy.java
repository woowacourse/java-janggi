package domain.piece.strategy;

import static domain.piece.strategy.Direction.DOWN;
import static domain.piece.strategy.Direction.LEFT;
import static domain.piece.strategy.Direction.LEFT_DOWN;
import static domain.piece.strategy.Direction.LEFT_UP;
import static domain.piece.strategy.Direction.RIGHT;
import static domain.piece.strategy.Direction.RIGHT_DOWN;
import static domain.piece.strategy.Direction.RIGHT_UP;
import static domain.piece.strategy.Direction.UP;

import java.util.List;

public class AllDirectionsSingleStepMoveStrategy extends SingleStepMoveStrategy {
    private static final List<Direction> DIRECTIONS = List.of(UP, DOWN, LEFT, RIGHT, LEFT_DOWN, LEFT_UP, RIGHT_UP,
            RIGHT_DOWN);

    @Override
    protected List<Direction> getDirections() {
        return DIRECTIONS;
    }
}

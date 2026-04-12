package domain.piece.strategy;

import static domain.piece.strategy.Direction.LEFT;
import static domain.piece.strategy.Direction.LEFT_UP;
import static domain.piece.strategy.Direction.RIGHT;
import static domain.piece.strategy.Direction.RIGHT_UP;
import static domain.piece.strategy.Direction.UP;

import java.util.List;

public class ByeongMoveStrategy extends SingleStepMoveStrategy {
    private static final List<Direction> DIRECTIONS = List.of(UP, LEFT, RIGHT, LEFT_UP, RIGHT_UP);

    @Override
    protected List<Direction> getDirections() {
        return DIRECTIONS;
    }
}

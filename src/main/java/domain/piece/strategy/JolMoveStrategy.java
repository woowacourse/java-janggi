package domain.piece.strategy;

import static domain.piece.strategy.Direction.DOWN;
import static domain.piece.strategy.Direction.LEFT;
import static domain.piece.strategy.Direction.LEFT_DOWN;
import static domain.piece.strategy.Direction.RIGHT;
import static domain.piece.strategy.Direction.RIGHT_DOWN;

import java.util.List;

public class JolMoveStrategy extends SingleStepMoveStrategy {
    private static final List<Direction> DIRECTIONS = List.of(DOWN, LEFT, RIGHT, LEFT_DOWN, RIGHT_DOWN);

    @Override
    protected List<Direction> getDirections() {
        return DIRECTIONS;
    }
}

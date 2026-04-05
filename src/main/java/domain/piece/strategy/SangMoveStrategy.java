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

public class SangMoveStrategy extends MultipleStepMoveStrategy {
    private final List<List<Direction>> DIRECTIONS = List.of(
            List.of(UP, LEFT_UP, LEFT_UP), List.of(UP, RIGHT_UP, RIGHT_UP),
            List.of(LEFT, LEFT_UP, LEFT_UP), List.of(LEFT, LEFT_DOWN, LEFT_DOWN),
            List.of(RIGHT, RIGHT_UP, RIGHT_UP), List.of(RIGHT, RIGHT_DOWN, RIGHT_DOWN),
            List.of(DOWN, LEFT_DOWN, LEFT_DOWN), List.of(DOWN, RIGHT_DOWN, RIGHT_DOWN));

    @Override
    List<List<Direction>> getDirections() {
        return DIRECTIONS;
    }
}

package domain.piece.strategy;

import static domain.piece.strategy.Direction.DOWN;
import static domain.piece.strategy.Direction.LEFT;
import static domain.piece.strategy.Direction.LEFT_DOWN;
import static domain.piece.strategy.Direction.LEFT_UP;
import static domain.piece.strategy.Direction.RIGHT;
import static domain.piece.strategy.Direction.RIGHT_DOWN;
import static domain.piece.strategy.Direction.RIGHT_UP;
import static domain.piece.strategy.Direction.UP;

import domain.position.Position;
import java.util.List;

public class SlidingMoveStrategy implements MoveStrategy {
    private static final List<Direction> DIRECTIONS = List.of(UP, LEFT, RIGHT, DOWN, LEFT_UP, RIGHT_UP, LEFT_DOWN,
            RIGHT_DOWN);

    @Override
    public List<Position> findMovablePath(Position start, Position destination) {
        return start.getPathToDestination(DIRECTIONS, destination);
    }
}

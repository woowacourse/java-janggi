package janggi.piece.path;

import janggi.piece.movement.MovementRule;
import janggi.piece.movement.StraightMovementRule;
import janggi.position.Direction;
import janggi.position.Position;
import java.util.ArrayList;
import java.util.List;

public class StraightMovementPathCalculator implements PathCalculator {

    private final MovementRule movementRule;

    public StraightMovementPathCalculator() {
        this.movementRule = new StraightMovementRule();
    }

    @Override
    public List<Position> calculatePath(final Position start, final Position end) {
        final Direction direction = Direction.calculateDirection(start, end);
        final List<Position> result = new ArrayList<>();
        for (Position path = start.move(direction);
             !path.equals(end);
             path = path.move(direction)) {
            movementRule.validateMovementRule(path, end);
            result.add(path);
        }
        return result;
    }
}

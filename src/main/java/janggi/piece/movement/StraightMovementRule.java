package janggi.piece.movement;

import janggi.position.Direction;
import janggi.position.Position;
import java.util.ArrayList;
import java.util.List;

public class StraightMovementRule implements MovementRule {

    @Override
    public List<Position> calculatePath(final Position start, final Position end) {
        final Direction direction = start.calculateDirection(end);
        final List<Position> result = new ArrayList<>();
        for (Position path = start.move(direction);
             !path.equals(end);
             path = path.move(direction)) {
            validateDirection(path, end);
            result.add(path);
        }
        return result;
    }

    @Override
    public void validateMovementRule(final Position start, final Position end) {
        validateDirection(start, end);
    }

    private void validateDirection(final Position start, final Position end) {
        final Direction direction = start.calculateDirection(end);
        if (start.hasDirection(direction)) {
            return;
        }
        throw new IllegalArgumentException("말의 이동 규칙과 어긋납니다.");
    }
}

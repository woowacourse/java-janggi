package janggi.piece.movement;

import janggi.position.Direction;
import janggi.position.Position;

public class StraightMovementRule implements MovementRule {

    @Override
    public void validateMovementRule(final Position start, final Position end) {
        validateDirection(start, end);
    }

    private void validateDirection(final Position start, final Position end) {
        final Direction direction = Direction.calculateDirection(start, end);
        if (start.hasDirection(direction)) {
            return;
        }
        throw new IllegalArgumentException("말의 이동 규칙과 어긋납니다.");
    }
}

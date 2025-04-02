package janggi.piece.movement;

import janggi.position.Position;

public class OneBlockMovementRule implements MovementRule {

    @Override
    public void validateMovementRule(final Position start, final Position end) {
        validateMoveDistance(start, end);
    }

    private void validateMoveDistance(final Position start, final Position end) {
        if (start.isMoveDistanceOneBlock(end)) {
            return;
        }
        throw new IllegalArgumentException("말의 이동 규칙과 어긋납니다.");
    }
}

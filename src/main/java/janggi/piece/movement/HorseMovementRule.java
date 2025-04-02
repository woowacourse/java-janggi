package janggi.piece.movement;

import janggi.position.Position;

public class HorseMovementRule implements MovementRule {

    @Override
    public void validateMovementRule(final Position start, final Position end) {
        final int absDifferenceX = start.calculateAbsoluteDifferenceX(end);
        final int absDifferenceY = start.calculateAbsoluteDifferenceY(end);
        if ((absDifferenceX == 2 && absDifferenceY == 1)
                || (absDifferenceX == 1 && absDifferenceY == 2)) {
            return;
        }
        throw new IllegalArgumentException("말의 이동 규칙과 어긋납니다.");
    }
}

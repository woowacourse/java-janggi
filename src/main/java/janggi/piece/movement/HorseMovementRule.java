package janggi.piece.movement;

import janggi.position.Position;
import java.util.List;

public class HorseMovementRule implements MovementRule {

    @Override
    public List<Position> calculatePath(final Position start, final Position end) {
        return List.of(calculateFirstPathPosition(start, end));
    }

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

    private Position calculateFirstPathPosition(final Position start, final Position end) {
        final int differenceX = start.calculateDifferenceX(end);
        final int differenceY = start.calculateDifferenceY(end);
        return start.offset(reduceOneTowardZero(differenceX), reduceOneTowardZero(differenceY));
    }

    private int reduceOneTowardZero(final int value) {
        final boolean isNegative = value < 0;
        final int absValue = Math.abs(value) - 1;
        if (isNegative) {
            return absValue * -1;
        }
        return absValue;
    }
}

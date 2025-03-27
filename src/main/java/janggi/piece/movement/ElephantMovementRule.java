package janggi.piece.movement;

import janggi.position.Position;
import java.util.List;

public class ElephantMovementRule implements MovementRule {

    @Override
    public List<Position> calculatePath(final Position start, final Position end) {
        final Position firstStep = calculateFirstPathPosition(start, end);
        final Position secondStep = calculateSecondPathPosition(start, end);
        return List.of(firstStep, secondStep);
    }

    @Override
    public void validateMovementRule(final Position start, final Position end) {
        final int absDifferenceX = start.calculateAbsoluteDifferenceX(end);
        final int absDifferenceY = start.calculateAbsoluteDifferenceY(end);
        if ((absDifferenceX == 3 && absDifferenceY == 2)
                || (absDifferenceX == 2 && absDifferenceY == 3)) {
            return;
        }
        throw new IllegalArgumentException("말의 이동 규칙과 어긋납니다.");
    }

    private Position calculateFirstPathPosition(final Position start, final Position end) {
        final int differenceX = start.calculateDifferenceX(end);
        final int differenceY = start.calculateDifferenceY(end);
        return start.offset(reduceValueTowardZero(differenceX, 2), reduceValueTowardZero(differenceY, 2));
    }

    private Position calculateSecondPathPosition(final Position start, final Position end) {
        final int differenceX = start.calculateDifferenceX(end);
        final int differenceY = start.calculateDifferenceY(end);
        return start.offset(reduceValueTowardZero(differenceX, 1), reduceValueTowardZero(differenceY, 1));
    }

    private int reduceValueTowardZero(final int value, final int reduceAmount) {
        final boolean isNegative = value < 0;
        final int reducedAbsoluteValue = Math.abs(value) - reduceAmount;
        if (isNegative) {
            return reducedAbsoluteValue * -1;
        }
        return reducedAbsoluteValue;
    }
}

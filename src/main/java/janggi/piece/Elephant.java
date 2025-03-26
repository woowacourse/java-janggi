package janggi.piece;

import janggi.position.Position;
import java.util.List;

public class Elephant extends Piece {

    public Elephant(final Color color) {
        super(color);
    }

    @Override
    public List<Position> calculatePath(final Position start, final Position end) {
        validateMovingRule(start, end);
        return findPath(start, end);
    }

    private void validateMovingRule(final Position start, final Position end) {
        int absDifferenceX = start.calculateAbsoluteDifferenceX(end);
        int absDifferenceY = start.calculateAbsoluteDifferenceY(end);
        if ((absDifferenceX == 3 && absDifferenceY == 2)
                || (absDifferenceX == 2 && absDifferenceY == 3)) {
            return;
        }
        throw new IllegalArgumentException("말의 이동 규칙과 어긋납니다.");
    }

    private List<Position> findPath(final Position start, final Position end) {
        Position firstStep = calculateFirstPathPosition(start, end);
        Position secondStep = calculateSecondPathPosition(start, end);
        return List.of(firstStep, secondStep);
    }

    private Position calculateFirstPathPosition(final Position start, final Position end) {
        int differenceX = start.calculateDifferenceX(end);
        int differenceY = start.calculateDifferenceY(end);
        return start.offset(reduceValueTowardZero(differenceX, 2), reduceValueTowardZero(differenceY, 2));
    }

    private Position calculateSecondPathPosition(final Position start, final Position end) {
        int differenceX = start.calculateDifferenceX(end);
        int differenceY = start.calculateDifferenceY(end);
        return start.offset(reduceValueTowardZero(differenceX, 1), reduceValueTowardZero(differenceY, 1));
    }

    private int reduceValueTowardZero(final int value, final int reduceAmount) {
        boolean isNegative = value < 0;
        int reducedAbsoluteValue = Math.abs(value) - reduceAmount;
        if (isNegative) {
            return reducedAbsoluteValue * -1;
        }
        return reducedAbsoluteValue;
    }
}

package janggi.piece.path;

import janggi.position.Position;
import java.util.List;

public class ElephantPathCalculator implements PathCalculator {

    @Override
    public List<Position> calculatePath(final Position start, final Position end) {
        final Position firstStep = calculateFirstPathPosition(start, end);
        final Position secondStep = calculateSecondPathPosition(start, end);
        return List.of(firstStep, secondStep);
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

package janggi.piece.path;

import janggi.position.Position;
import java.util.List;

public class HorsePathCalculator implements PathCalculator {

    @Override
    public List<Position> calculatePath(final Position start, final Position end) {
        return List.of(calculateFirstPathPosition(start, end));
    }

    private Position calculateFirstPathPosition(final Position start, final Position end) {
        final int differenceX = start.calculateDifferenceX(end);
        final int differenceY = start.calculateDifferenceY(end);
        return start.offset(reduceOneTowardZero(differenceX), reduceOneTowardZero(differenceY));
    }

    private int reduceOneTowardZero(final int value) {
        final boolean isNegative = value < 0;
        final int reducedAbsoluteValue = Math.abs(value) - 1;
        if (isNegative) {
            return reducedAbsoluteValue * -1;
        }
        return reducedAbsoluteValue;
    }
}

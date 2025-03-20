package janggi.piece;

import janggi.Position;
import janggi.Side;
import java.util.List;

public class Elephant extends Piece {

    public Elephant(final Side side) {
        super(side);
    }

    @Override
    public List<Position> calculatePath(final Position start, final Position end) {
        int differenceX = end.x() - start.x();
        int differenceY = end.y() - start.y();
        validateMovingRule(differenceX, differenceY);
        Position firstStep = calculateFirstDirection(start, differenceX, differenceY);
        Position secondStep = calculateSecondDirection(start, differenceX, differenceY);
        return List.of(firstStep, secondStep);
    }

    private Position calculateFirstDirection(final Position start, final int differenceX, final int differenceY) {
        return start.offset(reduceByAmount(differenceX, 2), reduceByAmount(differenceY, 2));
    }

    private Position calculateSecondDirection(final Position start, final int differenceX, final int differenceY) {
        return start.offset(reduceByAmount(differenceX, 1), reduceByAmount(differenceY, 1));
    }

    private int reduceByAmount(final int value, final int amount) {
        boolean isNegative = value < 0;
        int absValue = Math.abs(value) - amount;
        if (isNegative) {
            return absValue * -1;
        }
        return absValue;
    }

    private void validateMovingRule(final int differenceX, final int differenceY) {
        int absDifferenceX = Math.abs(differenceX);
        int absDifferenceY = Math.abs(differenceY);
        if ((absDifferenceX == 3 && absDifferenceY == 2) || (absDifferenceX == 2 && absDifferenceY == 3)) {
            return;
        }
        throw new IllegalArgumentException("말의 이동 규칙과 어긋납니다.");
    }
}

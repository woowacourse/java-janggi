package janggi.piece;

import janggi.Position;
import janggi.Side;
import java.util.List;

public class Horse extends Piece {

    public Horse(final Side side) {
        super(side);
    }

    @Override
    public List<Position> calculatePath(final Position start, final Position end) {
        int differenceX = end.x() - start.x();
        int differenceY = end.y() - start.y();
        validateMovingRule(differenceX, differenceY);
        return List.of(calculateDirection(start, differenceX, differenceY));
    }

    private Position calculateDirection(final Position start, final int differenceX, final int differenceY) {
        return start.offset(reduceOne(differenceX), reduceOne(differenceY));
    }

    private int reduceOne(final int value) {
        boolean isNegative = value < 0;
        int absValue = Math.abs(value) - 1;
        if (isNegative) {
            return absValue * -1;
        }
        return absValue;
    }

    private void validateMovingRule(final int differenceX, final int differenceY) {
        int absDifferenceX = Math.abs(differenceX);
        int absDifferenceY = Math.abs(differenceY);
        if ((absDifferenceX == 2 && absDifferenceY == 1)
                || (absDifferenceX == 1 && absDifferenceY == 2)) {
            return;
        }
        throw new IllegalArgumentException("말의 이동 규칙과 어긋납니다.");
    }
}

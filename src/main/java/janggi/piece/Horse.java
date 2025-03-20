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
        if (differenceX == 2) {
            return start.right(1);
        }
        if (differenceX == -2) {
            return start.left(1);
        }
        if (differenceY == 2) {
            return start.up(1);
        }
        return start.down(1);
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

package janggi.piece;

import janggi.position.Position;
import java.util.List;

public class Horse extends Piece {

    public Horse(final Color color) {
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
        if ((absDifferenceX == 2 && absDifferenceY == 1)
                || (absDifferenceX == 1 && absDifferenceY == 2)) {
            return;
        }
        throw new IllegalArgumentException("말의 이동 규칙과 어긋납니다.");
    }

    private List<Position> findPath(final Position start, final Position end) {
        return List.of(calculateFirstPathPosition(start, end));
    }

    private Position calculateFirstPathPosition(final Position start, final Position end) {
        int differenceX = start.calculateDifferenceX(end);
        int differenceY = start.calculateDifferenceY(end);
        return start.offset(reduceOneTowardZero(differenceX), reduceOneTowardZero(differenceY));
    }

    private int reduceOneTowardZero(final int value) {
        boolean isNegative = value < 0;
        int absValue = Math.abs(value) - 1;
        if (isNegative) {
            return absValue * -1;
        }
        return absValue;
    }
}

package janggi.piece;

import janggi.board.Position;
import java.util.List;
import java.util.Map;

public class Horse extends Piece {

    public Horse(final Side side) {
        super(side);
    }

    @Override
    public boolean canMove(final Position start, final Position end, final Map<Position, Piece> board) {
        return isEmptyOnPath(board, findPath(start, end)) && isValidMovingRule(start, end);
    }

    private boolean isEmptyOnPath(final Map<Position, Piece> board, final List<Position> path) {
        return path.stream()
                .noneMatch(board::containsKey);
    }

    private List<Position> findPath(final Position start, final Position end) {
        return List.of(findDirection(start, end));
    }

    private boolean isValidMovingRule(final Position start, final Position end) {
        int differenceX = end.x() - start.x();
        int differenceY = end.y() - start.y();
        int absDifferenceX = Math.abs(differenceX);
        int absDifferenceY = Math.abs(differenceY);
        return ((absDifferenceX == 2 && absDifferenceY == 1) || (absDifferenceX == 1 && absDifferenceY == 2));
    }

    private Position findDirection(final Position start, final Position end) {
        int differenceX = end.x() - start.x();
        int differenceY = end.y() - start.y();
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
}

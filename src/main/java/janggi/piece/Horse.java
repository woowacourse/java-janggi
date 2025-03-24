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

    @Override
    public boolean isCannon() {
        return false;
    }

    private boolean isEmptyOnPath(final Map<Position, Piece> board, final List<Position> path) {
        return path.stream()
                .noneMatch(board::containsKey);
    }

    private boolean isValidMovingRule(final Position start, final Position end) {
        int absDeltaX = start.absDeltaX(end);
        int absDeltaY = start.absDeltaY(end);
        return (absDeltaX == 2 && absDeltaY == 1) || (absDeltaX == 1 && absDeltaY == 2);
    }

    private List<Position> findPath(final Position start, final Position end) {
        return List.of(findDirection(start, end));
    }

    private Position findDirection(final Position start, final Position end) {
        int deltaX = start.deltaX(end);
        int deltaY = start.deltaY(end);
        return start.offset(stepTowardZero(deltaX), stepTowardZero(deltaY));
    }

    private int stepTowardZero(final int value) {
        boolean isNegative = value < 0;
        int absValue = Math.abs(value) - 1;
        if (isNegative) {
            return absValue * -1;
        }
        return absValue;
    }
}

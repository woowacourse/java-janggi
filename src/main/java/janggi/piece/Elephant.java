package janggi.piece;

import janggi.board.Position;
import java.util.List;
import java.util.Map;

public class Elephant extends Piece {

    public Elephant(final Side side) {
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
        return (absDeltaX == 3 && absDeltaY == 2) || (absDeltaX == 2 && absDeltaY == 3);
    }

    private List<Position> findPath(final Position start, final Position end) {
        int deltaX = start.deltaX(end);
        int deltaY = start.deltaY(end);
        Position firstStep = calculateFirstDirection(start, deltaX, deltaY);
        Position secondStep = calculateSecondDirection(start, deltaX, deltaY);
        return List.of(firstStep, secondStep);
    }

    private Position calculateFirstDirection(final Position start, final int deltaX, final int deltaY) {
        return start.offset(stepTowardZeroByAmount(deltaX, 2), stepTowardZeroByAmount(deltaY, 2));
    }

    private Position calculateSecondDirection(final Position start, final int deltaX, final int deltaY) {
        return start.offset(stepTowardZeroByAmount(deltaX, 1), stepTowardZeroByAmount(deltaY, 1));
    }

    private int stepTowardZeroByAmount(final int value, final int amount) {
        boolean isNegative = value < 0;
        int absValue = Math.abs(value) - amount;
        if (isNegative) {
            return absValue * -1;
        }
        return absValue;
    }
}

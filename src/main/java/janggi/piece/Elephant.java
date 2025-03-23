package janggi.piece;

import janggi.board.Position;
import java.util.List;
import java.util.Map;

public class Elephant extends Piece {

    public Elephant(final Side side) {
        super(side);
    }

    @Override
    public boolean canMove(Position start, Position end, Map<Position, Piece> board) {
        return isEmptyOnPath(board, findPath(start, end)) && isValidMovingRule(start, end);
    }

    private boolean isEmptyOnPath(Map<Position, Piece> board, List<Position> path) {
        return path.stream()
                .noneMatch(board::containsKey);
    }

    private List<Position> findPath(final Position start, final Position end) {
        int differenceX = end.x() - start.x();
        int differenceY = end.y() - start.y();
        Position firstStep = calculateFirstDirection(start, differenceX, differenceY);
        Position secondStep = calculateSecondDirection(start, differenceX, differenceY);
        return List.of(firstStep, secondStep);
    }

    private boolean isValidMovingRule(final Position start, final Position end) {
        int differenceX = end.x() - start.x();
        int differenceY = end.y() - start.y();
        int absDifferenceX = Math.abs(differenceX);
        int absDifferenceY = Math.abs(differenceY);
        return (absDifferenceX == 3 && absDifferenceY == 2) || (absDifferenceX == 2 && absDifferenceY == 3);
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
}

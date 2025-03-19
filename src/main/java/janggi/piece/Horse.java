package janggi.piece;

import janggi.Position;
import janggi.Side;
import java.util.List;

public class Horse extends Piece {

    public Horse(final Side side) {
        super(side);
    }

    @Override
    public boolean movable(final Position current, final Position destination, final List<Position> piecePositions) {
        int differenceX = destination.x() - current.x();
        int differenceY = destination.y() - current.y();
        Position blockingPosition = calculateBlockingPath(current, differenceX, differenceY);
        if (isPathBlocking(blockingPosition, piecePositions)) {
            return false;
        }
        return followingMovingRule(differenceX, differenceY);
    }

    private boolean isPathBlocking(final Position blockingPosition, final List<Position> piecePositions) {
        return piecePositions.stream()
                .anyMatch(position -> position.equals(blockingPosition));
    }

    private Position calculateBlockingPath(final Position current, final int differenceX,
                                           final int differenceY) {
        if (differenceX == 2) {
            return current.right(1);
        }
        if (differenceX == -2) {
            return current.left(1);
        }
        if (differenceY == 2) {
            return current.up(1);
        }
        return current.down(1);
    }

    private boolean followingMovingRule(final int differenceX, final int differenceY) {
        return (Math.abs(differenceX) == 2 && Math.abs(differenceY) == 1)
                || (Math.abs(differenceX) == 1 && Math.abs(differenceY) == 2);
    }
}

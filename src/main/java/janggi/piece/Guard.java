package janggi.piece;

import janggi.board.Position;
import java.util.Map;

public class Guard extends Piece {

    public Guard(final Side side) {
        super(side);
    }

    @Override
    public boolean canMove(Position start, Position end, Map<Position, Piece> board) {
        return isValidMovingRule(start, end);
    }

    private boolean isValidMovingRule(Position start, Position end) {
        int differenceX = end.x() - start.x();
        int differenceY = end.y() - start.y();
        int absDifferenceX = Math.abs(differenceX);
        int absDifferenceY = Math.abs(differenceY);
        return (absDifferenceX == 1 && absDifferenceY == 0) || (absDifferenceX == 0 && absDifferenceY == 1);
    }
}

package janggi.piece;

import janggi.board.Position;
import java.util.Map;

public class Guard extends Piece {

    public Guard(final Side side) {
        super(side);
    }

    @Override
    public boolean canMove(final Position start, final Position end, final Map<Position, Piece> board) {
        return isValidMovingRule(start, end);
    }

    @Override
    public boolean isCannon() {
        return false;
    }

    private boolean isValidMovingRule(final Position start, final Position end) {
        int absDeltaX = start.absDeltaX(end);
        int absDeltaY = start.absDeltaY(end);
        return (absDeltaX == 1 && absDeltaY == 0) || (absDeltaX == 0 && absDeltaY == 1);
    }
}

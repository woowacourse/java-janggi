package janggi.piece;

import janggi.board.Position;
import java.util.Map;

public class King extends Piece {

    public King(final Side side) {
        super(side);
    }

    @Override
    public boolean canMove(Position start, Position end, Map<Position, Piece> board) {
        return isValidMovingRule(start, end);
    }

    @Override
    public boolean isCannon() {
        return false;
    }

    private boolean isValidMovingRule(Position start, Position end) {
        int absDeltaX = start.absDeltaX(end);
        int absDeltaY = start.absDeltaY(end);
        return (absDeltaX == 1 && absDeltaY == 0) || (absDeltaX == 0 && absDeltaY == 1);
    }
}

package janggi.piece;

import janggi.board.Position;
import java.util.Map;

public class Soldier extends Piece {

    public Soldier(final Side side) {
        super(side);
    }

    @Override
    public boolean canMove(Position start, Position end, Map<Position, Piece> board) {
        return isValidMovingRule(start, end) && isValidDirection(start, end);
    }

    private boolean isValidMovingRule(final Position start, final Position end) {
        int differenceY = end.y() - start.y();
        int differenceX = end.x() - start.x();
        int absDifferenceX = Math.abs(differenceX);
        int absDifferenceY = Math.abs(differenceY);
        return ((absDifferenceX == 1 && absDifferenceY == 0) || (absDifferenceX == 0 && absDifferenceY == 1));
    }

    private boolean isValidDirection(Position start, Position end) {
        int differenceY = end.y() - start.y();
        return (side == Side.RED && differenceY <= 0) || (side == Side.BLUE && differenceY >= 0);
    }
}

package janggi.piece;

import janggi.board.Position;
import java.util.Map;

public class Soldier extends Piece {

    public Soldier(final Side side) {
        super(side);
    }

    @Override
    public boolean canMove(final Position start, final Position end, final Map<Position, Piece> board) {
        return isValidMovingRule(start, end) && isValidDirection(start, end);
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

    private boolean isValidDirection(final Position start, final Position end) {
        return (side == Side.RED && isValidRedSideDirection(start, end))
                || (side == Side.BLUE && isValidBlueSideDirection(start, end));
    }

    private boolean isValidRedSideDirection(Position start, Position end) {
        return start.isDown(end) || start.isHorizontalMove(end);
    }

    private boolean isValidBlueSideDirection(Position start, Position end) {
        return start.isUp(end) || start.isHorizontalMove(end);
    }
}

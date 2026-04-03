package domain.strategy;

import domain.Board;
import domain.vo.Position;

public class GuardMoveStrategy implements MoveStrategy {

    @Override
    public boolean canMove(final Position from, final Position to, final Board board) {
        if (isNotStraightPath(from, to))
            return false;

        return board.canOccupy(from, to);
    }

    private boolean isNotStraightPath(Position from, Position to) {
        if (from.getRow() == to.getRow() && Math.abs(from.getCol() - to.getCol()) == 1) {
            return false;
        }

        if (from.getCol() == to.getCol() && Math.abs(from.getRow() - to.getRow()) == 1) {
            return false;
        }
        return true;
    }
}

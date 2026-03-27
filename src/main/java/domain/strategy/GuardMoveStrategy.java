package domain.strategy;

import domain.Board;
import domain.vo.Position;

public class GuardMoveStrategy implements MoveStrategy {

    @Override
    public boolean canMove(final Position from, final Position to, final Board board) {
        if (from.getRow() == to.getRow() && Math.abs(from.getCol() - to.getCol()) != 1) {
            return false;
        }

        if (from.getCol() == to.getCol() && Math.abs(from.getRow() - to.getRow()) != 1) {
            return false;
        }

        if (board.isAnotherTeam(from, to)) {
            return true;
        }

        return false;
    }
}

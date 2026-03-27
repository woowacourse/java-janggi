package domain.strategy;

import domain.Board;
import domain.vo.Position;

public class GeneralMoveStrategy implements MoveStrategy {

    @Override
    public boolean canMove(final Position from, final Position to, final Board board) {
        if (isNotCorrectPath(from, to))
            return false;

        if (board.isAnotherTeam(from, to)) {
            return true;
        }
        return false;
    }

    private boolean isNotCorrectPath(final Position from, final Position to) {
        if (from.getRow() == to.getRow() && Math.abs(from.getCol() - to.getCol()) != 1) {
            return true;
        }

        if (from.getCol() == to.getCol() && Math.abs(from.getRow() - to.getRow()) != 1) {
            return true;
        }

        return false;
    }
}

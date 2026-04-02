package domain.strategy;

import domain.Board;
import domain.vo.Position;

public class ElephantMoveStrategy implements MoveStrategy {

    @Override
    public boolean canMove(final Position from, final Position to, final Board board) {
        if (isValidElephantMove(from, to))
            return false;

        int distanceX = to.getRow() - from.getRow();
        int distanceY = to.getCol() - from.getCol();

        int row = from.getRow();
        int col = from.getCol();

        if (board.isExistPosition(Position.of(row + distanceX / 3, col + distanceY / 3))) {
            return false;
        }

        if (board.isExistPosition(Position.of(row + distanceX / 3 + distanceX / 2, col + distanceY / 3 + distanceY / 2))) {
            return false;
        }

        return board.canOccupy(from, to);
    }

    private boolean isValidElephantMove(final Position from, final Position to) {
        if (Math.abs(from.getRow() - to.getRow()) == 2 && Math.abs(from.getCol() - to.getCol()) == 3) {
            return false;
        }
        if (Math.abs(from.getRow() - to.getRow()) == 3 && Math.abs(from.getCol() - to.getCol()) == 2) {
            return false;
        }
        return true;
    }
}

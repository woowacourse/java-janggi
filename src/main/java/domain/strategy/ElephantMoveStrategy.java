package domain.strategy;

import domain.board.Board;
import domain.vo.Position;

public class ElephantMoveStrategy implements MoveStrategy {

    @Override
    public boolean canMove(final Position from, final Position to, final Board board) {
        if (isNotValidElephantMove(from, to))
            return false;

        int distanceX = to.getRow() - from.getRow();
        int distanceY = to.getCol() - from.getCol();

        int row = from.getRow();
        int col = from.getCol();

        Position step1 = Position.of(row + distanceX / 3, col + distanceY / 3);
        if (board.isExistPosition(step1)) {
            return false;
        }

        Position step2 = Position.of(row + distanceX / 3 + distanceX / 2, col + distanceY / 3 + distanceY / 2);
        if (board.isExistPosition(step2)) {
            return false;
        }

        return board.canOccupy(from, to);
    }

    private boolean isNotValidElephantMove(final Position from, final Position to) {
        if (from.getDistanceRow(to) == 2 && from.getDistanceCol(to) == 3) {
            return false;
        }
        if (from.getDistanceRow(to) == 3 && from.getDistanceCol(to) == 2) {
            return false;
        }
        return true;
    }
}

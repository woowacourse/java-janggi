package domain.strategy;

import domain.Board;
import domain.vo.Position;

public class HorseMoveStrategy implements MoveStrategy {

    @Override
    public boolean canMove(final Position from, final Position to, final Board board) {
        if (isValidHorseMove(from, to))
            return false;

        int distanceX = to.getRow() - from.getRow();
        int distanceY = to.getCol() - from.getCol();

        if (board.isExistPosition(Position.of((from.getRow() + distanceX / 2), (from.getCol() + distanceY / 2)))) {
            return false;
        }

        return board.canOccupy(from, to);
    }

    private boolean isValidHorseMove(final Position from, final Position to) {
        if (Math.abs(from.getRow() - to.getRow()) == 1 && Math.abs(from.getCol() - to.getCol()) == 2) {
            return false;
        }

        if (Math.abs(from.getRow() - to.getRow()) == 2 && Math.abs(from.getCol() - to.getCol()) == 1) {
            return false;
        }

        return true;
    }
}

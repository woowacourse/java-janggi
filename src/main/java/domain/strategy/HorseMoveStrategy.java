package domain.strategy;

import domain.board.Board;
import domain.vo.Position;

public class HorseMoveStrategy implements MoveStrategy {

    @Override
    public boolean canMove(final Position from, final Position to, final Board board) {
        if (isNotValidHorseMove(from, to))
            return false;

        int distanceX = to.getRow() - from.getRow();
        int distanceY = to.getCol() - from.getCol();

        if (board.isExistPosition(Position.of((from.getRow() + distanceX / 2), (from.getCol() + distanceY / 2)))) {
            return false;
        }

        return board.canOccupy(from, to);
    }

    private boolean isNotValidHorseMove(final Position from, final Position to) {
        if (from.getDistanceRow(to) == 1 && from.getDistanceCol(to) == 2) {
            return false;
        }

        if (from.getDistanceRow(to) == 2 && from.getDistanceCol(to) == 1) {
            return false;
        }

        return true;
    }
}

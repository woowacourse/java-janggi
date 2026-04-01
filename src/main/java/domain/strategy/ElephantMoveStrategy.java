package domain.strategy;

import domain.Board;
import domain.vo.Position;

public class ElephantMoveStrategy implements MoveStrategy {

    @Override
    public boolean canMove(final Position from, final Position to, final Board board) {

        if (isNotCorrectPath(from, to))
            return false;

        int nx = to.getRow() - from.getRow();
        int ny = to.getCol() - from.getCol();

        int row = from.getRow();
        int col = from.getCol();

        if (board.isExistPosition(Position.of(row + nx / 3, col + ny / 3))) {
            return false;
        }

        if (board.isExistPosition(Position.of(row + nx / 3 + nx / 2, col + ny / 3 + ny / 2))) {
            return false;
        }

        if (board.isAnotherTeam(from, to)) {
            return true;
        }
        return false;
    }

    private boolean isNotCorrectPath(final Position from, final Position to) {
        int rowDiff = Math.abs(from.getRow() - to.getRow());
        int colDiff = Math.abs(from.getCol() - to.getCol());

        return !((rowDiff == 3 && colDiff == 2) || (rowDiff == 2 && colDiff == 3));
    }
}

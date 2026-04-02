package domain.strategy;

import domain.Board;
import domain.vo.Position;

public class ChariotMoveStrategy implements MoveStrategy {

    @Override
    public boolean canMove(final Position from, final Position to, final Board board) {
        if (isNotStraightPath(from, to))
            return false;

        int nx = Integer.compare(to.getRow(), from.getRow());
        int ny = Integer.compare(to.getCol(), from.getCol());

        int row = from.getRow();
        int col = from.getCol();

        while (row != to.getRow() || col != to.getCol()) {
            row += nx;
            col += ny;

            if (row == to.getRow() && col == to.getCol()) {
                break;
            }
            if (board.isExistPosition(Position.of(row, col))) {
                return false;
            }
        }

        return board.canOccupy(from, to);
    }

    private boolean isNotStraightPath(final Position from, final Position to) {
        return from.getCol() != to.getCol() && from.getRow() != to.getRow();
    }
}

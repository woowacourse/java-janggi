package domain.strategy;

import domain.board.Board;
import domain.vo.Position;

public class ChariotMoveStrategy implements MoveStrategy {

    @Override
    public boolean canMove(final Position from, final Position to, final Board board) {
        if (isNotStraightPath(from, to) && isNotDiagonalPath(from, to, board))
            return false;

        int dx = Integer.compare(to.getRow(), from.getRow());
        int dy = Integer.compare(to.getCol(), from.getCol());

        int row = from.getRow();
        int col = from.getCol();

        while (row != to.getRow() || col != to.getCol()) {
            row += dx;
            col += dy;

            if (row == to.getRow() && col == to.getCol()) {
                break;
            }
            if (board.isExistPosition(Position.of(row, col))) {
                return false;
            }
        }

        return board.canOccupy(from, to);
    }

    private boolean isNotDiagonalPath(Position from, Position to, Board board) {
        if (board.canMoveDiagonallyInPalace(from, to)
                && Math.abs(from.getRow() - to.getRow()) == Math.abs(from.getCol() - to.getCol())) {
            return false;
        }
        return true;
    }

    private boolean isNotStraightPath(final Position from, final Position to) {
        return from.getCol() != to.getCol() && from.getRow() != to.getRow();
    }
}

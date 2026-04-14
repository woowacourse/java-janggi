package domain.strategy;

import domain.board.Board;
import domain.vo.Position;

public class ChariotMoveStrategy implements MoveStrategy {

    @Override
    public boolean canMove(final Position from, final Position to, final Board board) {
        if (!from.isStraightTo(to)
                && !MoveValidator.canMoveDiagonal(from, to, board))
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
}

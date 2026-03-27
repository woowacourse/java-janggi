package domain.strategy;

import domain.Board;
import domain.vo.Position;

public class ChariotMoveStrategy implements MoveStrategy {

    @Override
    public boolean canMove(final Position from, final Position to, final Board board) {

        if (isNotCorrectPath(from, to))
            return false;

        int nx = 0, ny = 0;
        if (from.getRow() == to.getRow()) {
            if (from.getCol() < to.getCol()) {
                ny = 1;
            }
            if (from.getCol() > to.getCol()) {
                ny = -1;
            }
        }

        if (from.getCol() == to.getCol()) {
            if (from.getRow() < to.getRow()) {
                nx = 1;
            }
            if (from.getRow() > to.getRow()) {
                nx = -1;
            }
        }

        int row = from.getRow();
        int col = from.getCol();
        while (true) {
            row += nx;
            col += ny;

            if (row == to.getRow() && col == to.getCol()) {
                return board.isAnotherTeam(from, to);
            }
            if (board.isExistPosition(Position.of(row, col))) {
                return false;
            }
        }
    }

    private boolean isNotCorrectPath(final Position from, final Position to) {
        return from.getCol() != to.getCol() && from.getRow() != to.getRow();
    }
}

package janggi.domain.moveRule;

import janggi.domain.BoardView;
import janggi.domain.vo.Position;

public class TankMoveRule implements MoveRule {
    @Override
    public boolean canMove(Position from, Position to, BoardView board) {
        if (isStraightLine(from, to)) {
            return isStraightPathClear(board, from, to);
        }
        if (board.isDiagonalInPalace(from, to)) {
            return isDiagonalPathClear(board, from, to);
        }
        return false;
    }

    private boolean isDiagonalPathClear(BoardView board, Position from, Position to) {
        Position midpoint = board.getDiagonalMidpointInPalace(from, to);
        if (midpoint == null) {
            return true;
        }
        return board.isEmptyPosition(midpoint);
    }

    private boolean isStraightLine(Position from, Position to) {
        return from.getRow() == to.getRow() || from.getCol() == to.getCol();
    }


    private boolean isStraightPathClear(BoardView board, Position from, Position to) {
        int fromRow = from.getRow();
        int fromCol = from.getCol();
        int toRow = to.getRow();
        int toCol = to.getCol();

        if (fromRow == toRow) {
            return isHorizontalPathClear(board, fromRow, fromCol, toCol);
        }
        return isVerticalPathClear(board, fromCol, fromRow, toRow);
    }


    private boolean isHorizontalPathClear(BoardView board, int row, int fromCol, int toCol) {
        int start = Math.min(fromCol, toCol);
        int end = Math.max(fromCol, toCol);

        for (int col = start + 1; col < end; col++) {
            if (!board.isEmptyPosition(new Position(row, col))) {
                return false;
            }
        }
        return true;
    }

    private boolean isVerticalPathClear(BoardView board, int col, int fromRow, int toRow) {
        int start = Math.min(fromRow, toRow);
        int end = Math.max(fromRow, toRow);

        for (int row = start + 1; row < end; row++) {
            if (!board.isEmptyPosition(new Position(row, col))) {
                return false;
            }
        }
        return true;
    }
}

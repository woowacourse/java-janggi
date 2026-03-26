package janggi.domain.mouveRule;

import janggi.domain.BoardView;
import janggi.domain.vo.Position;

public class TankMoveRule implements MoveRule {
    @Override
    public boolean canMove(Position from, Position to, BoardView board) {
        int fromRow = from.getRow();
        int fromCol = from.getCol();
        int toRow = to.getRow();
        int toCol = to.getCol();

        if (!isStraightLine(fromRow, fromCol, toRow, toCol)) {
            return false;
        }

        return isPathClear(board, fromRow, fromCol, toRow, toCol);
    }

    private boolean isStraightLine(int fromRow, int fromCol, int toRow, int toCol) {
        return fromRow == toRow || fromCol == toCol;
    }

    private boolean isPathClear(BoardView board, int fromRow, int fromCol, int toRow, int toCol) {
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

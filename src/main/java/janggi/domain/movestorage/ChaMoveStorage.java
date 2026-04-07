package janggi.domain.movestorage;

import janggi.domain.BoardView;
import janggi.domain.Column;
import janggi.domain.Position;
import janggi.domain.Row;

public class ChaMoveStorage implements MoveStorage {

    @Override
    public boolean canMove(Position from, Position to, BoardView boardView) {
        if (isNotStraight(from, to)) {
            return false;
        }

        return !isPathBlocked(from, to, boardView);
    }

    private boolean isNotStraight(Position from, Position to) {
        return from.getRowValue() != to.getRowValue() && from.getColumnValue() != to.getColumnValue();
    }

    private boolean isPathBlocked(Position from, Position to, BoardView boardView) {
        if (from.getRowValue() == to.getRowValue()) {
             return isHorizontalPathBlocked(from, to, boardView);
        }
        return isVerticalPathBlocked(from, to, boardView);
    }

    private boolean isHorizontalPathBlocked(Position from, Position to, BoardView boardView) {
        int row = from.getRowValue();
        int start = Math.min(from.getColumnValue(), to.getColumnValue()) + 1;
        int end = Math.max(from.getColumnValue(), to.getColumnValue());

        for (int column = start; column < end; column++) {
            Position position = Position.of(Row.of(row), Column.of(column));
            if (boardView.hasPieceAt(position)) {
                return true;
            }
        }
        return false;
    }

    private boolean isVerticalPathBlocked(Position from, Position to, BoardView boardView) {
        int column = from.getColumnValue();
        int start = Math.min(from.getRowValue(), to.getRowValue()) + 1;
        int end = Math.max(from.getRowValue(), to.getRowValue());

        for (int row = start; row < end; row++) {
            Position position = Position.of(Row.of(row), Column.of(column));
            if (boardView.hasPieceAt(position)) {
                return true;
            }
        }
        return false;
    }
}

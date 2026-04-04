package janggi.domain.movestrategy.rule;

import janggi.domain.board.BoardState;
import janggi.domain.position.Column;
import janggi.domain.position.Position;
import janggi.domain.position.Row;

public class PalaceChaDiagonalRule implements MoveRule {

    @Override
    public boolean isValid(Position from, Position to, BoardState boardState) {
        if (!isBothInPalace(from, to)) {
            return false;
        }
        if (!isDiagonalShape(from, to)) {
            return false;
        }
        if (!isOnPalaceLines(from, to)) {
            return false;
        }
        if (hasObstacleAtCenter(from, to, boardState)) {
            return false;
        }
        return true;
    }

    private boolean isBothInPalace(Position from, Position to) {
        return isInPalace(from) && isInPalace(to);
    }

    private boolean isDiagonalShape(Position from, Position to) {
        int rowDiff = Math.abs(from.getRow() - to.getRow());
        int colDiff = Math.abs(from.getColumn() - to.getColumn());
        return rowDiff == colDiff && rowDiff > 0;
    }

    private boolean isOnPalaceLines(Position from, Position to) {
        boolean isCenterConnected = isCenter(from) || isCenter(to);
        boolean isCrossingCenter = (Math.abs(from.getRow() - to.getRow()) == 2);
        return isCenterConnected || isCrossingCenter;
    }

    private boolean hasObstacleAtCenter(Position from, Position to, BoardState boardState) {
        if (Math.abs(from.getRow() - to.getRow()) != 2) {
            return false;
        }

        int midRow = (from.getRow() + to.getRow()) / 2;
        int midCol = (from.getColumn() + to.getColumn()) / 2;
        Position midPos = Position.of(Row.of(midRow), Column.of(midCol));

        return boardState.hasPieceAt(midPos);
    }

    private boolean isInPalace(Position p) {
        boolean inColumn = (p.getColumn() >= 3 && p.getColumn() <= 5);
        boolean inHanPalace = (p.getRow() >= 0 && p.getRow() <= 2);
        boolean inChoPalace = (p.getRow() >= 7 && p.getRow() <= 9);
        return inColumn && (inHanPalace || inChoPalace);
    }

    private boolean isCenter(Position position) {
        return (position.getRow() == 1 && position.getColumn() == 4)
                || (position.getRow() == 8 && position.getColumn() == 4);
    }
}

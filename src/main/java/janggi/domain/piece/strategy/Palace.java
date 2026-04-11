package janggi.domain.piece.strategy;

import janggi.domain.Position;

public class Palace {

    private static final int CHO_ROW_MIN = 0;
    private static final int CHO_ROW_MAX = 2;
    private static final int HAN_ROW_MIN = 7;
    private static final int HAN_ROW_MAX = 9;

    private static final int COL_MIN = 3;
    private static final int COL_MAX = 5;

    private static final Position CHO_PALACE_CENTER = new Position(1, 4);
    private static final Position HAN_PALACE_CENTER = new Position(8, 4);

    public boolean isPalaceRange(Position source, Position destination) {
        return isInside(source) && isInside(destination);
    }

    private boolean isInside(Position position) {
        return isRowInRange(position) && isColInRange(position);
    }

    private boolean isRowInRange(Position position) {
        return (CHO_ROW_MIN <= position.row() && position.row() <= CHO_ROW_MAX)
                || (HAN_ROW_MIN <= position.row() && position.row() <= HAN_ROW_MAX);
    }

    private boolean isColInRange(Position position) {
        return COL_MIN <= position.column() && position.column() <= COL_MAX;
    }

    public boolean isAllowedDiagonalPath(Position source, Position destination) {
        return isDiagonalPoint(source) && isDiagonalPoint(destination);
    }

    private boolean isDiagonalPoint(Position position) {
        return position.equals(CHO_PALACE_CENTER) || isCorner(position, CHO_ROW_MIN, CHO_ROW_MAX)
                || position.equals(HAN_PALACE_CENTER) || isCorner(position, HAN_ROW_MIN, HAN_ROW_MAX);
    }

    private boolean isCorner(Position position, int rowMin, int rowMax) {
        return (position.row() == rowMin || position.row() == rowMax)
                && (position.column() == COL_MIN || position.column() == COL_MAX);
    }
}

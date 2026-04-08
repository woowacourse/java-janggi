package domain.position;

public final class Palace {
    private static final int PALACE_MIN_COLUMN = 3;
    private static final int PALACE_MAX_COLUMN = 5;
    private static final int TOP_PALACE_MIN_ROW = 0;
    private static final int TOP_PALACE_MAX_ROW = 2;
    private static final int BOTTOM_PALACE_MIN_ROW = 7;
    private static final int BOTTOM_PALACE_MAX_ROW = 9;

    private static final Position TOP_LEFT = new Position(0, 3);
    private static final Position TOP_CENTER = new Position(1, 4);
    private static final Position TOP_RIGHT = new Position(0, 5);
    private static final Position TOP_BOTTOM_LEFT = new Position(2, 3);
    private static final Position TOP_BOTTOM_RIGHT = new Position(2, 5);

    private static final Position BOTTOM_TOP_LEFT = new Position(7, 3);
    private static final Position BOTTOM_CENTER = new Position(8, 4);
    private static final Position BOTTOM_TOP_RIGHT = new Position(7, 5);
    private static final Position BOTTOM_BOTTOM_LEFT = new Position(9, 3);
    private static final Position BOTTOM_BOTTOM_RIGHT = new Position(9, 5);

    private Palace() {
    }

    public static boolean isInPalace(Position position) {
        return isInTopPalace(position) || isInBottomPalace(position);
    }

    public static boolean isDiagonalReachable(Position source, Position destination) {
        if (!isInPalace(source) || !isInPalace(destination)) {
            return false;
        }

        return isOnSamePalaceDiagonalLine(source, destination, TOP_LEFT, TOP_CENTER, TOP_BOTTOM_RIGHT)
                || isOnSamePalaceDiagonalLine(source, destination, TOP_RIGHT, TOP_CENTER, TOP_BOTTOM_LEFT)
                || isOnSamePalaceDiagonalLine(source, destination, BOTTOM_TOP_LEFT, BOTTOM_CENTER, BOTTOM_BOTTOM_RIGHT)
                || isOnSamePalaceDiagonalLine(source, destination, BOTTOM_TOP_RIGHT, BOTTOM_CENTER, BOTTOM_BOTTOM_LEFT);
    }

    private static boolean isInTopPalace(Position position) {
        return isInPalaceColumnRange(position) && isInPalaceRowRange(position, TOP_PALACE_MIN_ROW, TOP_PALACE_MAX_ROW);
    }

    private static boolean isInBottomPalace(Position position) {
        return isInPalaceColumnRange(position) && isInPalaceRowRange(position, BOTTOM_PALACE_MIN_ROW, BOTTOM_PALACE_MAX_ROW);
    }

    private static boolean isInPalaceRowRange(Position position, int minRow, int maxRow) {
        return position.row() >= minRow && position.row() <= maxRow;
    }

    private static boolean isInPalaceColumnRange(Position position) {
        return position.column() >= PALACE_MIN_COLUMN && position.column() <= PALACE_MAX_COLUMN;
    }

    private static boolean isOnSamePalaceDiagonalLine(Position source, Position destination, Position first, Position middle, Position last) {
        return isInLinePair(source, destination, first, middle)
                || isInLinePair(source, destination, middle, last)
                || isInLinePair(source, destination, first, last);
    }

    private static boolean isInLinePair(Position source, Position destination, Position start, Position end) {
        return (source.equals(start) && destination.equals(end))
                || (source.equals(end) && destination.equals(start));
    }
}


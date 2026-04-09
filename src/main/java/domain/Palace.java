package domain;

public class Palace {
    private static final int PALACE_MIN_ROW_BOTTOM = 1;
    private static final int PALACE_MAX_ROW_BOTTOM = 3;

    private static final int PALACE_MIN_ROW_TOP = 8;
    private static final int PALACE_MAX_ROW_TOP = 10;

    private static final int PALACE_MIN_COLUMN = 4;
    private static final int PALACE_MAX_COLUMN = 6;

    private static final int BOTTOM_PALACE_CENTER_ROW = 2;
    private static final int TOP_PALACE_CENTER_ROW = 9;
    private static final int PALACE_CENTER_COLUMN = 5;

    public static boolean isPalace(Position position) {
        return isRangeOfPalaceBottom(position) || isRangeOfPalaceTop(position);
    }

    public static boolean isPalaceCorner(Position position) {
        if (!isPalace(position)) return false;
        return isCornerOfPalace(position);
    }

    public static boolean isPalaceCenter(Position position) {
        int row = position.getRow();
        int column = position.getColumn();
        return (row == BOTTOM_PALACE_CENTER_ROW || row == TOP_PALACE_CENTER_ROW) && column == PALACE_CENTER_COLUMN;
    }

    private static boolean isRangeOfPalaceTop(Position position) {
        int row = position.getRow();
        int column = position.getColumn();

        return row <= PALACE_MAX_ROW_TOP && row >= PALACE_MIN_ROW_TOP && column >= PALACE_MIN_COLUMN && column <= PALACE_MAX_COLUMN;
    }

    private static boolean isRangeOfPalaceBottom(Position position) {
        int row = position.getRow();
        int column = position.getColumn();

        return row >= PALACE_MIN_ROW_BOTTOM && row <= PALACE_MAX_ROW_BOTTOM && column >= PALACE_MIN_COLUMN && column <= PALACE_MAX_COLUMN;
    }

    private static boolean isCornerOfPalace(Position position) {
        int row = position.getRow();
        int column = position.getColumn();

        if (row != PALACE_MAX_ROW_TOP && row != PALACE_MIN_ROW_TOP && row != PALACE_MIN_ROW_BOTTOM && row != PALACE_MAX_ROW_BOTTOM) {
            return false;
        }

        return column == PALACE_MAX_COLUMN || column == PALACE_MIN_COLUMN;
    }
}

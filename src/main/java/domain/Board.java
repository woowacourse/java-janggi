package domain;

public class Board {

    public static int START_ROW_INDEX = 1;
    public static int END_ROW_INDEX = 10;
    public static int START_COLUMN_INDEX = 1;
    public static int END_COLUMN_INDEX = 9;

    public static boolean isInRange(int row, int column) {
        return !(row < START_ROW_INDEX || row > END_ROW_INDEX)
                || (column < START_COLUMN_INDEX || column > END_COLUMN_INDEX);
    }
}

package domain.board;

public class Palace {
    private static final int PALACE_MIN_COLUMN = 3;
    private static final int PALACE_MAX_COLUMN = 5;
    private static final int CHO_PALACE_MIN_ROW = 0;
    private static final int CHO_PALACE_MAX_ROW = 2;
    private static final int HAN_PALACE_MIN_ROW = 7;
    private static final int HAN_PALACE_MAX_ROW = 9;

    public static boolean isPalace(Position position) {
        return isChoPalace(position) || isHanPalace(position);
    }

    public static boolean isChoPalace(Position position) {
        return isPalaceColumn(position.column()) && isChoPalaceRow(position.row());
    }

    public static boolean isHanPalace(Position position) {
        return isPalaceColumn(position.column()) && isHanPalaceRow(position.row());
    }

    private static boolean isPalaceColumn(int column) {
        return column >= PALACE_MIN_COLUMN && column <= PALACE_MAX_COLUMN;
    }


    private static boolean isChoPalaceRow(int row) {
        return row >= CHO_PALACE_MIN_ROW && row <= CHO_PALACE_MAX_ROW;
    }

    private static boolean isHanPalaceRow(int row) {
        return row >= HAN_PALACE_MIN_ROW && row <= HAN_PALACE_MAX_ROW;
    }
}

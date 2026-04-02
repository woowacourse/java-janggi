package domain.board;

public class BoardPolicy {

    public static final int MIN_ROW = 1;
    public static final int MAX_ROW = 10;
    public static final int MIN_COLUMN = 1;
    public static final int MAX_COLUMN = 9;

    public static boolean isOutBoundPosition(int row, int col) {
        return isOutBoundRow(row) || isOutBoundColumn(col);
    }

    private static boolean isOutBoundRow(int row){
        return row < MIN_ROW || row > MAX_ROW ;
    }

    private static boolean isOutBoundColumn(int col){
        return col < MIN_COLUMN || col > MAX_COLUMN;
    }
}

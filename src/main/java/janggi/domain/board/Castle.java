package janggi.domain.board;

import janggi.domain.point.Point;

public class Castle {

    private static final int HAN_CASTLE_ZONE_ROW_MIN = 7;
    private static final int HAN_CASTLE_ZONE_ROW_MAX = 9;
    private static final int CHO_CASTLE_ZONE_ROW_MIN =0;
    private static final int CHO_CASTLE_ZONE_ROW_MAX =2;
    private static final int CASTLE_ZONE_COLUMN_MIN = 3;
    private static final int CASTLE_ZONE_COLUMN_MAX = 5;

    public static boolean inSameCastle(Point to, Point from) {
        if (isCastle(from.getColumn(), from.getRow()) && isCastle(to.getColumn(), to.getRow())) {
            return (from.getRow() <= CHO_CASTLE_ZONE_ROW_MAX) == (to.getRow() <= CHO_CASTLE_ZONE_ROW_MAX);
        }
        return false;
    }

    private static boolean isCastle(int column, int row) {
        boolean isColumnCastle = column >= CASTLE_ZONE_COLUMN_MIN && column <= CASTLE_ZONE_COLUMN_MAX;
        boolean isRowHanCastle = row >= HAN_CASTLE_ZONE_ROW_MIN && row <= HAN_CASTLE_ZONE_ROW_MAX;
        boolean isRowChoCastle = row <= CHO_CASTLE_ZONE_ROW_MAX && row >= CHO_CASTLE_ZONE_ROW_MIN;
        return isColumnCastle && (isRowHanCastle || isRowChoCastle);
    }
}

package model.coordinate;

import java.util.Set;

public final class PalacePositions {

    public static final int PALACE_MIN_COL = 3;
    public static final int PALACE_MAX_COL = 5;

    public static final int HAN_PALACE_MIN_ROW = 0;
    public static final int HAN_PALACE_MAX_ROW = 2;

    public static final int CHO_PALACE_MIN_ROW = 7;
    public static final int CHO_PALACE_MAX_ROW = 9;

    public static final Position HAN_PALACE_TOP_LEFT = new Position(HAN_PALACE_MIN_ROW, PALACE_MIN_COL);
    public static final Position HAN_PALACE_TOP_RIGHT = new Position(HAN_PALACE_MIN_ROW, PALACE_MAX_COL);
    public static final Position HAN_PALACE_CENTER = new Position(1, 4);
    public static final Position HAN_PALACE_BOTTOM_LEFT = new Position(HAN_PALACE_MAX_ROW, PALACE_MIN_COL);
    public static final Position HAN_PALACE_BOTTOM_RIGHT = new Position(HAN_PALACE_MAX_ROW, PALACE_MAX_COL);

    public static final Position CHO_PALACE_TOP_LEFT = new Position(CHO_PALACE_MIN_ROW, PALACE_MIN_COL);
    public static final Position CHO_PALACE_TOP_RIGHT = new Position(CHO_PALACE_MIN_ROW, PALACE_MAX_COL);
    public static final Position CHO_PALACE_CENTER = new Position(8, 4);
    public static final Position CHO_PALACE_BOTTOM_LEFT = new Position(CHO_PALACE_MAX_ROW, PALACE_MIN_COL);
    public static final Position CHO_PALACE_BOTTOM_RIGHT = new Position(CHO_PALACE_MAX_ROW, PALACE_MAX_COL);

    private static final Set<Position> DIAGONAL_POSITIONS = Set.of(
            HAN_PALACE_TOP_LEFT, HAN_PALACE_TOP_RIGHT, HAN_PALACE_CENTER,
            HAN_PALACE_BOTTOM_LEFT, HAN_PALACE_BOTTOM_RIGHT,
            CHO_PALACE_TOP_LEFT, CHO_PALACE_TOP_RIGHT, CHO_PALACE_CENTER,
            CHO_PALACE_BOTTOM_LEFT, CHO_PALACE_BOTTOM_RIGHT
    );

    private PalacePositions() {
    }

    public static boolean onPalaceDiagonal(Position position) {
        return DIAGONAL_POSITIONS.contains(position);
    }

    public static boolean inPalace(Position position) {
        return inHanPalace(position) || inChoPalace(position);
    }

    private static boolean inHanPalace(Position position) {
        return position.row() >= HAN_PALACE_MIN_ROW && position.row() <= HAN_PALACE_MAX_ROW
                && position.col() >= PALACE_MIN_COL && position.col() <= PALACE_MAX_COL;
    }

    private static boolean inChoPalace(Position position) {
        return position.row() >= CHO_PALACE_MIN_ROW && position.row() <= CHO_PALACE_MAX_ROW
                && position.col() >= PALACE_MIN_COL && position.col() <= PALACE_MAX_COL;
    }
}

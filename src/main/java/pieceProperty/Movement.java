package pieceProperty;

public enum Movement {
    UP(-1, 0),
    DOWN(1, 0),
    RIGHT(0, 1),
    LEFT(0, -1),

    RIGHT_UP_DIAGONAL(UP.dRow, RIGHT.dCol),
    LEFT_UP_DIAGONAL(UP.dRow, LEFT.dCol),
    RIGHT_DOWN_DIAGONAL(DOWN.dRow, RIGHT.dCol),
    LEFT_DOWN_DIAGONAL(DOWN.dRow, LEFT.dCol),

    UP_RIGHT_UP_DIAGONAL(UP.dRow + RIGHT_UP_DIAGONAL.dRow, RIGHT_UP_DIAGONAL.dCol),
    UP_LEFT_UP_DIAGONAL(UP.dRow + LEFT_UP_DIAGONAL.dRow, LEFT_UP_DIAGONAL.dCol),
    RIGHT_RIGHT_UP_DIAGONAL(RIGHT_UP_DIAGONAL.dRow, RIGHT.dCol + RIGHT_UP_DIAGONAL.dCol),
    RIGHT_RIGHT_DOWN_DIAGONAL(RIGHT_DOWN_DIAGONAL.dRow, RIGHT.dCol + RIGHT_DOWN_DIAGONAL.dCol),
    DOWN_RIGHT_DOWN_DIAGONAL(DOWN.dRow + RIGHT_DOWN_DIAGONAL.dRow, RIGHT_DOWN_DIAGONAL.dCol),
    DOWN_LEFT_DOWN_DIAGONAL(DOWN.dRow + LEFT_DOWN_DIAGONAL.dRow, LEFT_DOWN_DIAGONAL.dCol),
    LEFT_LEFT_UP_DIAGONAL(LEFT_UP_DIAGONAL.dRow, LEFT.dCol + LEFT_UP_DIAGONAL.dCol),
    LEFT_LEFT_DOWN_DIAGONAL(LEFT_DOWN_DIAGONAL.dRow, LEFT.dCol + LEFT_DOWN_DIAGONAL.dCol),

    UP_RIGHT_UP_DIAGONAL_UP_RIGHT_UP_DIAGONAL(-3,2),
    UP_LEFT_UP_DIAGONAL_LEFT_UP_DIAGONAL(-3, -2),
    RIGHT_RIGHT_UP_DIAGONAL_RIGHT_UP_DIAGONAL(-2, 3),
    RIGHT_RIGHT_DOWN_DIAGONAL_RIGHT_DOWN_DIAGONAL(2, 3),
    DOWN_RIGHT_DOWN_DIAGONAL_RIGHT_DOWN_DIAGONAL(3, 2),
    DOWN_LEFT_DOWN_DIAGONAL_LEFT_DOWN_DIAGONAL(3, -2),
    LEFT_LEFT_UP_DIAGONAL_LEFT_UP_DIAGONAL(-2, -3),
    LEFT_LEFT_DOWN_DIAGONAL_LEFT_DOWN__DIAGONAL(2, -3);


    private final int dRow;
    private final int dCol;

    Movement(int dRow, int dCol) {
        this.dRow = dRow;
        this.dCol = dCol;
    }

    public static Position calculateUpMovement(final Position presentPosition) {
        return presentPosition.calculateMovement(UP.dRow, UP.dCol);
    }

    public static Position calculateDownMovement(final Position presentPosition) {
        return presentPosition.calculateMovement(DOWN.dRow, DOWN.dCol);
    }

    public static Position calculateRightMovement(final Position presentPosition) {
        return presentPosition.calculateMovement(RIGHT.dRow, RIGHT.dCol);
    }

    public static Position calculateLeftMovement(final Position presentPosition) {
        return presentPosition.calculateMovement(LEFT.dRow, LEFT.dCol);
    }

    public static Position calculateRightUpMovement(final Position presentPosition) {
        return presentPosition.calculateMovement(RIGHT_UP_DIAGONAL.dRow, RIGHT_UP_DIAGONAL.dCol);
    }

    public static Position calculateRightDownMovement(final Position presentPosition) {
        return presentPosition.calculateMovement(RIGHT_DOWN_DIAGONAL.dRow, RIGHT_DOWN_DIAGONAL.dCol);
    }

    public static Position calculateLeftUpMovement(final Position presentPosition) {
        return presentPosition.calculateMovement(LEFT_UP_DIAGONAL.dRow, LEFT_UP_DIAGONAL.dCol);
    }

    public static Position calculateLeftDownMovement(final Position presentPosition) {
        return presentPosition.calculateMovement(LEFT_DOWN_DIAGONAL.dRow, LEFT_DOWN_DIAGONAL.dCol);
    }

    public static Position calculateUpRightUPMovement(final Position presentPosition) {
        return presentPosition.calculateMovement(UP_RIGHT_UP_DIAGONAL.dRow, UP_RIGHT_UP_DIAGONAL.dCol);
    }

    public static Position calculateUpLeftUpMovement(final Position presentPosition) {
        return presentPosition.calculateMovement(UP_LEFT_UP_DIAGONAL.dRow, UP_LEFT_UP_DIAGONAL.dCol);
    }

    public static Position calculateRightRightUpMovement(final Position presentPosition) {
        return presentPosition.calculateMovement(RIGHT_RIGHT_UP_DIAGONAL.dRow, RIGHT_RIGHT_UP_DIAGONAL.dCol);
    }

    public static Position calculateRightRightDownMovement(final Position presentPosition) {
        return presentPosition.calculateMovement(RIGHT_RIGHT_DOWN_DIAGONAL.dRow, RIGHT_RIGHT_DOWN_DIAGONAL.dCol);
    }

    public static Position calculateDownRightDownMovement(final Position presentPosition) {
        return presentPosition.calculateMovement(DOWN_RIGHT_DOWN_DIAGONAL.dRow, DOWN_RIGHT_DOWN_DIAGONAL.dCol);
    }

    public static Position calculateDownLeftDownMovement(final Position presentPosition) {
        return presentPosition.calculateMovement(DOWN_LEFT_DOWN_DIAGONAL.dRow, DOWN_LEFT_DOWN_DIAGONAL.dCol);
    }

    public static Position calculateLeftLeftUpMovement(final Position presentPosition) {
        return presentPosition.calculateMovement(LEFT_LEFT_UP_DIAGONAL.dRow, LEFT_LEFT_UP_DIAGONAL.dCol);
    }

    public static Position calculateLeftLeftDownMovement(final Position presentPosition) {
        return presentPosition.calculateMovement(LEFT_LEFT_DOWN_DIAGONAL.dRow, LEFT_LEFT_DOWN_DIAGONAL.dCol);
    }

    public static Position calculateUpRightUpRightUpMovement(final Position presentPosition) {
        return presentPosition.calculateMovement(UP_RIGHT_UP_DIAGONAL_UP_RIGHT_UP_DIAGONAL.dRow,
                UP_RIGHT_UP_DIAGONAL_UP_RIGHT_UP_DIAGONAL.dCol);
    }

    public static Position calculateUpLeftUpLeftUpMovement(final Position presentPosition) {
        return presentPosition.calculateMovement(UP_LEFT_UP_DIAGONAL_LEFT_UP_DIAGONAL.dRow,
                UP_LEFT_UP_DIAGONAL_LEFT_UP_DIAGONAL.dCol);
    }

    public static Position calculateRightRightUpRightUpMovement(final Position presentPosition) {
        return presentPosition.calculateMovement(RIGHT_RIGHT_UP_DIAGONAL_RIGHT_UP_DIAGONAL.dRow,
                RIGHT_RIGHT_UP_DIAGONAL_RIGHT_UP_DIAGONAL.dCol);
    }

    public static Position calculateRightRightDownRightDownMovement(final Position presentPosition) {
        return presentPosition.calculateMovement(RIGHT_RIGHT_DOWN_DIAGONAL_RIGHT_DOWN_DIAGONAL.dRow,
                RIGHT_RIGHT_DOWN_DIAGONAL_RIGHT_DOWN_DIAGONAL.dCol);
    }

    public static Position calculateDownRightDownRightDownMovement(final Position presentPosition) {
        return presentPosition.calculateMovement(DOWN_RIGHT_DOWN_DIAGONAL_RIGHT_DOWN_DIAGONAL.dRow,
                DOWN_RIGHT_DOWN_DIAGONAL_RIGHT_DOWN_DIAGONAL.dCol);
    }

    public static Position calculateDownLeftDownLeftDownMovement(final Position presentPosition) {
        return presentPosition.calculateMovement(DOWN_LEFT_DOWN_DIAGONAL_LEFT_DOWN_DIAGONAL.dRow,
                DOWN_LEFT_DOWN_DIAGONAL_LEFT_DOWN_DIAGONAL.dCol);
    }

    public static Position calculateLeftLeftUpLeftUpMovement(final Position presentPosition) {
        return presentPosition.calculateMovement(LEFT_LEFT_UP_DIAGONAL_LEFT_UP_DIAGONAL.dRow,
                LEFT_LEFT_UP_DIAGONAL_LEFT_UP_DIAGONAL.dCol);
    }

    public static Position calculateLeftLeftDownLeftDownMovement(final Position presentPosition) {
        return presentPosition.calculateMovement(LEFT_LEFT_DOWN_DIAGONAL_LEFT_DOWN__DIAGONAL.dRow,
                LEFT_LEFT_DOWN_DIAGONAL_LEFT_DOWN__DIAGONAL.dCol);
    }

    public static boolean isUpLeftUpLeftUp(final int dRow, final int dCol) {
        return dRow == 3 && dCol == 2;
    }

    public static boolean isUpRightUpRightUp(final int dRow, final int dCol) {
        return dRow == 3 && dCol == -2;
    }

    public static boolean isRightUpRightUpRight (final int dRow, final int dCol) {
        return dRow == 2 && dCol == -3;
    }

    public static boolean isRightRightDownRightDown(final int dRow, final int dCol) {
        return dRow == -2 && dCol == -3;
    }

    public static boolean isDownRightDownRightDown(final int dRow, final int dCol) {
        return dRow == -3 && dCol == -2;
    }

    public static boolean isDownLeftDownLeftDown(final int dRow, final int dCol) {
        return dRow == -3 && dCol == 2;
    }

    public static boolean isLeftLeftUpLeftUp(final int dRow, final int dCol) {
        return dRow == 2 && dCol == 3;
    }

    public static boolean isLeftLeftDownLeftDown(final int dRow, final int dCol) {
        return dRow == -2 && dCol == 3;
    }

    public static boolean isUpLeftUp(final int dRow, final int dCol) {
        return dRow == 2 && dCol == 1;
    }

    public static boolean isUpRightUp(final int dRow, final int dCol) {
        return dRow == 2 && dCol == -1;
    }

    public static boolean isRightRightUp(final int dRow, final int dCol) {
        return dRow == 1 && dCol == - 2;
    }

    public static boolean isRightRightDown(final int dRow, final int dCol) {
        return dRow == -1 && dCol == -2;
    }

    public static boolean isDownRightDown(final int dRow, final int dCol) {
        return dRow == -2 && dCol == -1;
    }

    public static boolean isDownLeftDown(final int dRow, final int dCol) {
        return dRow == -2 && dCol == 1;
    }

    public static boolean isLeftLeftDown(final int dRow, final int dCol) {
        return dRow == -1 && dCol == 2;
    }

    public static boolean isLeftLeftUp(final int dRow, final int dCol) {
        return dRow == 1 && dCol == 2;
    }

    public static boolean isLeftward(final int dRow, final int dCol) {
        return dRow == 0 && dCol > 0;
    }

    public static boolean isRightward(final int dRow, final int dCol) {
        return dRow == 0 && dCol < 0;
    }

    public static boolean isUpward(final int dRow, final int dCol) {
        return dRow > 0 && dCol == 0;
    }

    public static boolean isDownward(final int dRow, final int dCol) {
        return dRow < 0 && dCol == 0;
    }

    public int getDRow() {
        return dRow;
    }

    public int getDCol() {
        return dCol;
    }
}

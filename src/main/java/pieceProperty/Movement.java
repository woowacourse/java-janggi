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

    public static Position calculateUpMovement(Position presentPosition) {
        return presentPosition.calculateMovement(UP.dRow, UP.dCol);
    }

    public static Position calculateDownMovement(Position presentPosition) {
        return presentPosition.calculateMovement(DOWN.dRow, DOWN.dCol);
    }

    public static Position calculateRightMovement(Position presentPosition) {
        return presentPosition.calculateMovement(RIGHT.dRow, RIGHT.dCol);
    }

    public static Position calculateLeftMovement(Position presentPosition) {
        return presentPosition.calculateMovement(LEFT.dRow, LEFT.dCol);
    }

    public static Position calculateRightUpMovement(Position presentPosition) {
        return presentPosition.calculateMovement(RIGHT_UP_DIAGONAL.dRow, RIGHT_UP_DIAGONAL.dCol);
    }

    public static Position calculateRightDownMovement(Position presentPosition) {
        return presentPosition.calculateMovement(RIGHT_DOWN_DIAGONAL.dRow, RIGHT_DOWN_DIAGONAL.dCol);
    }

    public static Position calculateLeftUpMovement(Position presentPosition) {
        return presentPosition.calculateMovement(LEFT_UP_DIAGONAL.dRow, LEFT_UP_DIAGONAL.dCol);
    }

    public static Position calculateLeftDownMovement(Position presentPosition) {
        return presentPosition.calculateMovement(LEFT_DOWN_DIAGONAL.dRow, LEFT_DOWN_DIAGONAL.dCol);
    }

    public static Position calculateUpRightUPMovement(Position presentPosition) {
        return presentPosition.calculateMovement(UP_RIGHT_UP_DIAGONAL.dRow, UP_RIGHT_UP_DIAGONAL.dCol);
    }

    public static Position calculateUpLeftUpMovement(Position presentPosition) {
        return presentPosition.calculateMovement(UP_LEFT_UP_DIAGONAL.dRow, UP_LEFT_UP_DIAGONAL.dCol);
    }

    public static Position calculateRightRightUpMovement(Position presentPosition) {
        return presentPosition.calculateMovement(RIGHT_RIGHT_UP_DIAGONAL.dRow, RIGHT_RIGHT_UP_DIAGONAL.dCol);
    }

    public static Position calculateRightRightDownMovement(Position presentPosition) {
        return presentPosition.calculateMovement(RIGHT_RIGHT_DOWN_DIAGONAL.dRow, RIGHT_RIGHT_DOWN_DIAGONAL.dCol);
    }

    public static Position calculateDownRightDownMovement(Position presentPosition) {
        return presentPosition.calculateMovement(DOWN_RIGHT_DOWN_DIAGONAL.dRow, DOWN_RIGHT_DOWN_DIAGONAL.dCol);
    }

    public static Position calculateDownLeftDownMovement(Position presentPosition) {
        return presentPosition.calculateMovement(DOWN_LEFT_DOWN_DIAGONAL.dRow, DOWN_LEFT_DOWN_DIAGONAL.dCol);
    }

    public static Position calculateLeftLeftUpMovement(Position presentPosition) {
        return presentPosition.calculateMovement(LEFT_LEFT_UP_DIAGONAL.dRow, LEFT_LEFT_UP_DIAGONAL.dCol);
    }

    public static Position calculateLeftLeftDownMovement(Position presentPosition) {
        return presentPosition.calculateMovement(LEFT_LEFT_DOWN_DIAGONAL.dRow, LEFT_LEFT_DOWN_DIAGONAL.dCol);
    }

    public static Position calculateUpRightUpRightUpMovement(Position presentPosition) {
        return presentPosition.calculateMovement(UP_RIGHT_UP_DIAGONAL_UP_RIGHT_UP_DIAGONAL.dRow,
                UP_RIGHT_UP_DIAGONAL_UP_RIGHT_UP_DIAGONAL.dCol);
    }

    public static Position calculateUpLeftUpLeftUpMovement(Position presentPosition) {
        return presentPosition.calculateMovement(UP_LEFT_UP_DIAGONAL_LEFT_UP_DIAGONAL.dRow,
                UP_LEFT_UP_DIAGONAL_LEFT_UP_DIAGONAL.dCol);
    }

    public static Position calculateRightRightUpRightUpMovement(Position presentPosition) {
        return presentPosition.calculateMovement(RIGHT_RIGHT_UP_DIAGONAL_RIGHT_UP_DIAGONAL.dRow,
                RIGHT_RIGHT_UP_DIAGONAL_RIGHT_UP_DIAGONAL.dCol);
    }

    public static Position calculateRightRightDownRightDownMovement(Position presentPosition) {
        return presentPosition.calculateMovement(RIGHT_RIGHT_DOWN_DIAGONAL_RIGHT_DOWN_DIAGONAL.dRow,
                RIGHT_RIGHT_DOWN_DIAGONAL_RIGHT_DOWN_DIAGONAL.dCol);
    }

    public static Position calculateDownRightDownRightDownMovement(Position presentPosition) {
        return presentPosition.calculateMovement(DOWN_RIGHT_DOWN_DIAGONAL_RIGHT_DOWN_DIAGONAL.dRow,
                DOWN_RIGHT_DOWN_DIAGONAL_RIGHT_DOWN_DIAGONAL.dCol);
    }

    public static Position calculateDownLeftDownLeftDownMovement(Position presentPosition) {
        return presentPosition.calculateMovement(DOWN_LEFT_DOWN_DIAGONAL_LEFT_DOWN_DIAGONAL.dRow,
                DOWN_LEFT_DOWN_DIAGONAL_LEFT_DOWN_DIAGONAL.dCol);
    }

    public static Position calculateLeftLeftUpLeftUpMovement(Position presentPosition) {
        return presentPosition.calculateMovement(LEFT_LEFT_UP_DIAGONAL_LEFT_UP_DIAGONAL.dRow,
                LEFT_LEFT_UP_DIAGONAL_LEFT_UP_DIAGONAL.dCol);
    }

    public static Position calculateLeftLeftDownLeftDownMovement(Position presentPosition) {
        return presentPosition.calculateMovement(LEFT_LEFT_DOWN_DIAGONAL_LEFT_DOWN__DIAGONAL.dRow,
                LEFT_LEFT_DOWN_DIAGONAL_LEFT_DOWN__DIAGONAL.dCol);
    }

    public int getDRow() {
        return dRow;
    }

    public int getDCol() {
        return dCol;
    }
}

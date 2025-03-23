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

    public static Position upMovement(Position presentPosition) {
        return presentPosition.calculateMovement(UP.dRow, UP.dCol);
    }

    public static Position downMovement(Position presentPosition) {
        return presentPosition.calculateMovement(DOWN.dRow, DOWN.dCol);
    }

    public static Position rightMovement(Position presentPosition) {
        return presentPosition.calculateMovement(RIGHT.dRow, RIGHT.dCol);
    }

    public static Position leftMovement(Position presentPosition) {
        return presentPosition.calculateMovement(LEFT.dRow, LEFT.dCol);
    }

    public static Position rightUpMovement(Position presentPosition) {
        return presentPosition.calculateMovement(RIGHT_UP_DIAGONAL.dRow, RIGHT_UP_DIAGONAL.dCol);
    }

    public static Position rightDownMovement(Position presentPosition) {
        return presentPosition.calculateMovement(RIGHT_DOWN_DIAGONAL.dRow, RIGHT_DOWN_DIAGONAL.dCol);
    }

    public static Position leftUpMovement(Position presentPosition) {
        return presentPosition.calculateMovement(LEFT_UP_DIAGONAL.dRow, LEFT_UP_DIAGONAL.dCol);
    }

    public static Position leftDownMovement(Position presentPosition) {
        return presentPosition.calculateMovement(LEFT_DOWN_DIAGONAL.dRow, LEFT_DOWN_DIAGONAL.dCol);
    }

    public static Position upRightUPMovement(Position presentPosition) {
        return presentPosition.calculateMovement(UP_RIGHT_UP_DIAGONAL.dRow, UP_RIGHT_UP_DIAGONAL.dCol);
    }

    public static Position upLeftUpMovement(Position presentPosition) {
        return presentPosition.calculateMovement(UP_LEFT_UP_DIAGONAL.dRow, UP_LEFT_UP_DIAGONAL.dCol);
    }

    public static Position rightRightUpMovement(Position presentPosition) {
        return presentPosition.calculateMovement(RIGHT_RIGHT_UP_DIAGONAL.dRow, RIGHT_RIGHT_UP_DIAGONAL.dCol);
    }

    public static Position rightRightDownMovement(Position presentPosition) {
        return presentPosition.calculateMovement(RIGHT_RIGHT_DOWN_DIAGONAL.dRow, RIGHT_RIGHT_DOWN_DIAGONAL.dCol);
    }

    public static Position downRightDownMovement(Position presentPosition) {
        return presentPosition.calculateMovement(DOWN_RIGHT_DOWN_DIAGONAL.dRow, DOWN_RIGHT_DOWN_DIAGONAL.dCol);
    }

    public static Position downLeftDownMovement(Position presentPosition) {
        return presentPosition.calculateMovement(DOWN_LEFT_DOWN_DIAGONAL.dRow, DOWN_LEFT_DOWN_DIAGONAL.dCol);
    }

    public static Position leftLeftUpMovement(Position presentPosition) {
        return presentPosition.calculateMovement(LEFT_LEFT_UP_DIAGONAL.dRow, LEFT_LEFT_UP_DIAGONAL.dCol);
    }

    public static Position leftLeftDownMovement(Position presentPosition) {
        return presentPosition.calculateMovement(LEFT_LEFT_DOWN_DIAGONAL.dRow, LEFT_LEFT_DOWN_DIAGONAL.dCol);
    }

    public static Position upRightUpRightUpMovement(Position presentPosition) {
        return presentPosition.calculateMovement(UP_RIGHT_UP_DIAGONAL_UP_RIGHT_UP_DIAGONAL.dRow,
                UP_RIGHT_UP_DIAGONAL_UP_RIGHT_UP_DIAGONAL.dCol);
    }

    public static Position upLeftUpLeftUpMovement(Position presentPosition) {
        return presentPosition.calculateMovement(UP_LEFT_UP_DIAGONAL_LEFT_UP_DIAGONAL.dRow,
                UP_LEFT_UP_DIAGONAL_LEFT_UP_DIAGONAL.dCol);
    }

    public static Position rightRightUpRightUpMovement(Position presentPosition) {
        return presentPosition.calculateMovement(RIGHT_RIGHT_UP_DIAGONAL_RIGHT_UP_DIAGONAL.dRow,
                RIGHT_RIGHT_UP_DIAGONAL_RIGHT_UP_DIAGONAL.dCol);
    }

    public static Position rightRightDownRightDownMovement(Position presentPosition) {
        return presentPosition.calculateMovement(RIGHT_RIGHT_DOWN_DIAGONAL_RIGHT_DOWN_DIAGONAL.dRow,
                RIGHT_RIGHT_DOWN_DIAGONAL_RIGHT_DOWN_DIAGONAL.dCol);
    }

    public static Position downRightDownRightDownMovement(Position presentPosition) {
        return presentPosition.calculateMovement(DOWN_RIGHT_DOWN_DIAGONAL_RIGHT_DOWN_DIAGONAL.dRow,
                DOWN_RIGHT_DOWN_DIAGONAL_RIGHT_DOWN_DIAGONAL.dCol);
    }

    public static Position downLeftDownLeftDownMovement(Position presentPosition) {
        return presentPosition.calculateMovement(DOWN_LEFT_DOWN_DIAGONAL_LEFT_DOWN_DIAGONAL.dRow,
                DOWN_LEFT_DOWN_DIAGONAL_LEFT_DOWN_DIAGONAL.dCol);
    }

    public static Position leftLeftUpLeftUpMovement(Position presentPosition) {
        return presentPosition.calculateMovement(LEFT_LEFT_UP_DIAGONAL_LEFT_UP_DIAGONAL.dRow,
                LEFT_LEFT_UP_DIAGONAL_LEFT_UP_DIAGONAL.dCol);
    }

    public static Position leftLeftDownLeftDownMovement(Position presentPosition) {
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

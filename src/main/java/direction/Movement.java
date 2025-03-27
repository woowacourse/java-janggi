package direction;

public enum Movement {
    LEFT(-1, 0),
    RIGHT(1, 0),
    UP(0, -1),
    DOWN(0, 1),
    UP_UP_LEFT(-1, -2),
    UP_UP_RIGHT(1, -2),
    RIGHT_RIGHT_UP(2, -1),
    RIGHT_RIGHT_DOWN(2, 1),
    DOWN_DOWN_LEFT(-1, 2),
    DOWN_DOWN_RIGHT(1, 2),
    LEFT_LEFT_UP(-2, -1),
    LEFT_LEFT_DOWN(-2, 1),
    LEFT_LEFT_LEFT_UP_UP(-3, -2),
    LEFT_LEFT_LEFT_DOWN_DOWN(-3, 2),
    DOWN_DOWN_DOWN_LEFT_LEFT(-2, 3),
    DOWN_DOWN_DOWN_RIGHT_RIGHT(2, 3),
    RIGHT_RIGHT_RIGHT_UP_UP(3, -2),
    RIGHT_RIGHT_RIGHT_DOWN_DOWN(3, 2),
    UP_UP_UP_LEFT_LEFT(-2, -3),
    UP_UP_UP_RIGHT_RIGHT(2, -3)
    ;

    private final int column;
    private final int row;

    Movement(int column, int row) {
        this.column = column;
        this.row = row;
    }

    public static Movement toDirection(Point distance) {
        int columnDirection = Integer.signum(distance.column());
        int rowDirection = Integer.signum(distance.row());

        if (LEFT.column == columnDirection && LEFT.row == rowDirection) {
            return LEFT;
        }

        if (RIGHT.column == columnDirection && RIGHT.row == rowDirection) {
            return RIGHT;
        }

        if (UP.column == columnDirection && UP.row == rowDirection) {
            return UP;
        }

        return DOWN;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }
}

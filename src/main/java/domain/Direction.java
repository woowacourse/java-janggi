package domain;

public enum Direction {
    UP,
    DOWN,
    RIGHT,
    LEFT,
    RIGHT_UP,
    RIGHT_DOWN,
    LEFT_UP,
    LEFT_DOWN;

    public static Direction getDirection(JanggiCoordinate from, JanggiCoordinate to) {
        if (from.isSameRow(to)) {
            return getHorizontalDirection(from, to);
        }
        return getVerticalDirection(from, to);
    }

    public static Direction getDirection(JanggiCoordinate from, JanggiCoordinate to, int distance) {
        if (from.row() + distance == to.row()) {
            return Direction.DOWN;
        }
        if (from.row() - distance == to.row()) {
            return Direction.UP;
        }
        if (from.col() + distance == to.col()) {
            return Direction.RIGHT;
        }
        return Direction.LEFT;
    }

    public static Direction getDiagonalDirection(JanggiCoordinate from, JanggiCoordinate to) {
        if (from.row() > to.row() && from.col() > to.col()) {
            return Direction.LEFT_UP;
        }
        if (from.row() > to.row() && from.col() < to.col()) {
            return Direction.RIGHT_UP;
        }
        if (from.row() < to.row() && from.col() > to.col()) {
            return Direction.LEFT_DOWN;
        }
        return Direction.RIGHT_DOWN;
    }

    private static Direction getVerticalDirection(JanggiCoordinate from, JanggiCoordinate to) {
        if (from.row() > to.row()) {
            return Direction.UP;
        }
        return Direction.DOWN;
    }

    private static Direction getHorizontalDirection(JanggiCoordinate from, JanggiCoordinate to) {
        if (from.col() > to.col()) {
            return Direction.LEFT;
        }
        return Direction.RIGHT;
    }
}

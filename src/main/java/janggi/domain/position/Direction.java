package janggi.domain.position;

public enum Direction {

    NORTH(-1, 0),
    NORTHEAST(-1, 1),
    EAST(0, 1),
    SOUTHEAST(1, 1),
    SOUTH(1, 0),
    SOUTHWEST(1, -1),
    WEST(0, -1),
    NORTHWEST(-1, -1);

    private static final int DIRECTION_SIZE = Direction.values().length;
    private final int row;
    private final int column;

    Direction(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public static Direction[] valuesFourDirection() {
        return new Direction[]{NORTH, EAST, SOUTH, WEST};
    }

    public static Direction[] valuesFourDiagonalDirection() {
        return new Direction[]{NORTHEAST, SOUTHEAST, SOUTHWEST, NORTHWEST};
    }

    public Direction next() {
        return values()[(this.ordinal() + 1) % DIRECTION_SIZE];
    }

    public Direction prev() {
        return values()[(this.ordinal() + DIRECTION_SIZE - 1) % DIRECTION_SIZE];
    }

    public Direction back() {
        return values()[(this.ordinal() + 4) % DIRECTION_SIZE];
    }

    public int row() {
        return row;
    }

    public int column() {
        return column;
    }

}

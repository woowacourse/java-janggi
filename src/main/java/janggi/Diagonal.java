package janggi;

public class Diagonal {

    private final Direction vertical;
    private final Direction horizontal;

    public Diagonal(Direction vertical, Direction horizontal) {
        if (vertical == Direction.WEST || vertical == Direction.EAST) {
            throw new IllegalArgumentException("vertical은 수직 방향이어야 합니다.");
        }

        if (horizontal == Direction.SOUTH || horizontal == Direction.NORTH) {
            throw new IllegalArgumentException("horizontal 수평 방향이어야 합니다.");
        }

        this.vertical = vertical;
        this.horizontal = horizontal;
    }

    public static Diagonal of(int rowDistance, int columnDistance) {
        Direction vertical = Direction.NORTH;

        if (rowDistance == 1) {
            vertical = Direction.SOUTH;
        }

        Direction horizontal = Direction.WEST;

        if (columnDistance == 1) {
            horizontal = Direction.EAST;
        }

        return new Diagonal(vertical, horizontal);
    }

    public boolean isNorthAndWest() {
        return vertical == Direction.NORTH && horizontal == Direction.WEST;
    }

    public boolean isNorthAndEast() {
        return vertical == Direction.NORTH && horizontal == Direction.EAST;
    }

    public boolean isSouthAndWest() {
        return vertical == Direction.SOUTH && horizontal == Direction.WEST;
    }

    public boolean isSouthAndEast() {
        return vertical == Direction.SOUTH && horizontal == Direction.EAST;
    }
}

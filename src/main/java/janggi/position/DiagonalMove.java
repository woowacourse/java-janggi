package janggi.position;

public class DiagonalMove {

    private final Direction vertical;
    private final Direction horizontal;

    private DiagonalMove(Direction vertical, Direction horizontal) {
        if (vertical == Direction.WEST || vertical == Direction.EAST) {
            throw new IllegalArgumentException("vertical은 수직 방향이어야 합니다.");
        }

        if (horizontal == Direction.SOUTH || horizontal == Direction.NORTH) {
            throw new IllegalArgumentException("horizontal 수평 방향이어야 합니다.");
        }

        this.vertical = vertical;
        this.horizontal = horizontal;
    }

    public static DiagonalMove of(int rowDistance, int columnDistance) {
        if (Math.abs(rowDistance) != 1 || Math.abs(columnDistance) != 1) {
            throw new IllegalArgumentException("대각선이 아닙니다.");
        }

        Direction vertical = Direction.NORTH;

        if (rowDistance == 1) {
            vertical = Direction.SOUTH;
        }

        Direction horizontal = Direction.WEST;

        if (columnDistance == 1) {
            horizontal = Direction.EAST;
        }

        return new DiagonalMove(vertical, horizontal);
    }

    public boolean isNorth() {
        return vertical == Direction.NORTH;
    }

    public boolean isEast() {
        return horizontal == Direction.EAST;
    }
}

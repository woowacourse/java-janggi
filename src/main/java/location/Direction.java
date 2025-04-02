package location;

import java.util.Arrays;

public enum Direction {
    LEFT(-1, 0),
    RIGHT(1, 0),
    UP(0, -1),
    DOWN(0, 1),
    UP_LEFT_DIAGONAL(-1, -1),
    DOWN_LEFT_DIAGONAL(-1, 1),
    UP_RIGHT_DIAGONAL(1, -1),
    DOWN_RIGHT_DIAGONAL(1, 1);

    private final int row;
    private final int column;

    Direction(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public static Direction findBy(Position from, Position to) {
        return Direction.findBy(
                Integer.signum(to.x() - from.x()),
                Integer.signum(to.y() - from.y()));
    }

    private static Direction findBy(int compareRow, int compareColumn) {
        return Arrays.stream(Direction.values())
                .filter(direction -> direction.row == compareRow && direction.column == compareColumn)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 방향이 존재하지 않습니다."));
    }

    public static boolean isDiagonal(Position from, Position to) {
        Direction direction = Direction.findBy(from, to);
        return direction.getX() != 0 && direction.getY() != 0;
    }

    public int getX() {
        return row;
    }

    public int getY() {
        return column;
    }
}

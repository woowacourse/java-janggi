package direction;

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

    public static Direction find(Point from, Point to) {
        Point distance = to.minus(from);
        return Direction.findBy(Integer.signum(distance.x()), Integer.signum(distance.y()));
    }

    public int getX() {
        return row;
    }

    public int getY() {
        return column;
    }

    private static Direction findBy(int compareRow, int compareColumn) {
        return Arrays.stream(Direction.values())
                .filter(direction -> direction.row == compareRow && direction.column == compareColumn)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 방향이 존재하지 않습니다."));
    }
}

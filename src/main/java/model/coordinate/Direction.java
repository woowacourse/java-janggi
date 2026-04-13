package model.coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public enum Direction {
    EAST(0, 1),
    WEST(0, -1),
    NORTH(-1, 0),
    SOUTH(1, 0),
    NORTH_EAST(-1, 1),
    SOUTH_EAST(1, 1),
    NORTH_WEST(-1, -1),
    SOUTH_WEST(1, -1);

    private final int row;
    private final int col;

    Direction(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public static Direction of(int rowDiff, int colDiff) {
        return Stream.of(values())
                .filter(direction -> direction.isSameDirection(rowDiff, colDiff))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("제자리는 이동할 수 없는 방향입니다."));
    }

    private boolean isSameDirection(int rowDiff, int colDiff) {
        return row == Integer.signum(rowDiff) && col == Integer.signum(colDiff);
    }

    public List<Position> pathTo(Position start, Position end) {
        List<Position> path = new ArrayList<>();
        Position step = move(start);
        while (!step.equals(end)) {
            path.add(step);
            step = move(step);
        }
        return path;
    }

    public Position move(Position target) {
        return target.resolveNext(row, col);
    }
}

package direction;

import java.util.Arrays;

public enum Direction {
    LEFT(new Point(-1, 0)),
    RIGHT(new Point(1, 0)),
    UP(new Point(0, -1)),
    DOWN(new Point(0, 1)),
    UP_LEFT_DIAGONAL(new Point(-1, -1)),
    DOWN_LEFT_DIAGONAL(new Point(-1, 1)),
    UP_RIGHT_DIAGONAL(new Point(1, -1)),
    DOWN_RIGHT_DIAGONAL(new Point(1, 1));

    private final Point direction;

    Direction(Point direction) {
        this.direction = direction;
    }

    public static Direction find(Point from, Point to) {
        Point distance = to.minus(from);
        Point compareDirection = new Point(Integer.signum(distance.x()), Integer.signum(distance.y()));
        return Arrays.stream(Direction.values())
                .filter(value -> value.direction.equals(compareDirection))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 방향이 존재하지 않습니다."));
    }

    public Point getDirection() {
        return direction;
    }

    public Point apply(int side) {
        return direction.apply(side);
    }
}

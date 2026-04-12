package janggi.domain.rule.route;

import janggi.domain.board.Location;
import java.util.Arrays;

public enum Direction {

    FRONT(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0),
    BACK(0, -1),
    FRONT_LEFT(-1, 1),
    FRONT_RIGHT(1, 1),
    BACK_LEFT(-1, -1),
    BACK_RIGHT(1, -1);

    private final int dx;
    private final int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public static Direction getDirection(Location from, Location to) {
        int dx = Integer.compare(to.col(), from.col());
        int dy = Integer.compare(to.row(), from.row());

        return Arrays.stream(values())
                .filter(direction -> direction.dx == dx && direction.dy == dy)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("올바른 방향을 계산할 수 없습니다."));
    }

    public Location apply(Location location) {
        return location.add(dy, dx);
    }

    public boolean isSameDirection(Direction direction) {
        return this == direction;
    }
}

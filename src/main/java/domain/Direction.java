package domain;

import java.util.Arrays;

public enum Direction {
    UP(0, 1),
    DOWN(0, -1),
    LEFT(-1, 0),
    RIGHT(1, 0),
    UP_RIGHT(1, 1),
    DOWN_RIGHT(1, -1),
    UP_LEFT(-1, 1),
    DOWN_LEFT(-1, -1);


    private final Offset offset;

    Direction(int dx, int dy) {
        this.offset = new Offset(dx, dy);
    }

    public Offset unit() {
        return this.offset;
    }

    public static Direction of(Offset offset) {
        return Arrays.stream(values())
                .filter(direction -> direction.offset.equals(offset.normalize()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당하는 방향이 없습니다. (offset: " + offset + ")"));
    }

    public boolean containsBackWardDirection(Direction direction) {
        return (offset.dy() == direction.offset.dy());
    }
}

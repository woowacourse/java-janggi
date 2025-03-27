package janggi.domain;

import java.util.Arrays;

public enum Direction {

    UP(new Vector(-1, 0)),
    DOWN(new Vector(1, 0)),
    LEFT(new Vector(0, -1)),
    RIGHT(new Vector(0, 1)),
    LEFT_UP(new Vector(-1, -1)),
    LEFT_DOWN(new Vector(1, -1)),
    RIGHT_UP(new Vector(-1, 1)),
    RIGHT_DOWN(new Vector(1, 1)),
    DOWN_DOWN_LEFT(new Vector(2, -1)),
    DOWN_DOWN_RIGHT(new Vector(2, 1)),
    DOWN_DOWN_DOWN_LEFT_LEFT(new Vector(3, -2)),
    DOWN_DOWN_DOWN_RIGHT_RIGHT(new Vector(3, 2)),
    ;

    private final Vector vector;

    Direction(Vector vector) {
        this.vector = vector;
    }

    public Vector getVector() {
        return vector;
    }

    public Direction getDirection(Vector vector) {
        return Arrays.stream(Direction.values())
                .filter(value -> value.vector.equals(vector))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 값입니다."));
    }

    public Direction rotate() {
        Vector vector = getVector();
        return getDirection(vector);
    }
}

package janggi.domain;

import java.util.Objects;

public record Position(
    int x,
    int y
) {

    public int deltaX(Position other) {
        return other.x - x;
    }

    public int deltaY(Position other) {
        return other.y - y;
    }
}

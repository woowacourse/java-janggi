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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}

package janggi.domain;

import java.util.Objects;

public record Position(
        int x,
        int y
) {

    public int diffX(Position other) {
        return x - other.x;
    }

    public int diffY(Position other) {
        return y - other.y;
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

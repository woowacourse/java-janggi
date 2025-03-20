package domain;

import java.util.Objects;

public record Vector(
        int dx,
        int dy
) {

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vector vector)) {
            return false;
        }
        return dx == vector.dx && dy == vector.dy;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dx, dy);
    }
}

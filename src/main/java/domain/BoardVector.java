package domain;

import java.util.Objects;

public record BoardVector(
        int dx,
        int dy
) {

    public boolean isDxZero() {
        return dx == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BoardVector boardVector)) {
            return false;
        }
        return dx == boardVector.dx && dy == boardVector.dy;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dx, dy);
    }
}

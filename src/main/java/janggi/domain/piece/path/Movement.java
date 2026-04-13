package janggi.domain.piece.path;

import java.util.List;
import java.util.Objects;

public final class Movement {
    private final List<Direction> directions;

    public Movement(List<Direction> directions) {
        this.directions = directions;
    }

    public Movement(Direction direction) {
        this.directions = List.of(direction);
    }

    public List<Direction> getDirections() {
        return directions;
    }

    @Override
    public int hashCode() {
        return Objects.hash(directions);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (Movement) obj;
        return Objects.equals(this.directions, that.directions);
    }

    @Override
    public String toString() {
        return "Movement[" +
                "pattern=" + directions + ']';
    }


}

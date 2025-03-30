package janggi.position;

import java.util.HashSet;
import java.util.Set;

public class Directions {
    private final Set<Direction> directions;

    public Directions(final Set<Direction> directions) {
        this.directions = new HashSet<>(directions);
    }

    public Set<Direction> getDirections() {
        return new HashSet<>(directions);
    }

    public boolean contains(Direction direction) {
        return directions.contains(direction);
    }
}

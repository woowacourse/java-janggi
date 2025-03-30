package janggi.domain.movement;

import janggi.domain.Coordinate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Path {

    private final List<Coordinate> coordinates;
    private final boolean isReachable;

    public Path(final List<Coordinate> coordinates) {
        this(coordinates, true);
    }

    public Path(final List<Coordinate> coordinates, final boolean isReachable) {
        this.coordinates = new ArrayList<>(coordinates);
        this.isReachable = isReachable;
    }

    public List<Coordinate> coordinates() {
        return Collections.unmodifiableList(coordinates);
    }

    public boolean isReachable() {
        return isReachable;
    }

    public static Path unreachable() {
        return new Path(Collections.emptyList(), false);
    }
}

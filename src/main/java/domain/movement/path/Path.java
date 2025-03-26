package domain.movement.path;

import domain.Coordinate;
import java.util.Collections;
import java.util.List;

public class Path {

    private final List<Coordinate> coordinates;
    private final boolean isReachable;

    public Path(final List<Coordinate> coordinates) {
        this(coordinates, true);
    }

    public Path(final List<Coordinate> coordinates, final boolean isReachable) {
        this.coordinates = coordinates;
        this.isReachable = isReachable;
    }

    public List<Coordinate> coordinates() {
        if (isReachable()) {
            return List.copyOf(coordinates);
        }
        return Collections.emptyList();
    }

    public boolean isReachable() {
        return isReachable;
    }

    public static Path unreachable() {
        return new Path(null, false);
    }
}

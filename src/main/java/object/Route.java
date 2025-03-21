package object;

import java.util.Collections;
import java.util.List;

public class Route {

    private final List<Coordinate> coordinates;

    public Route(final List<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }

    public List<Coordinate> getPositions() {
        return Collections.unmodifiableList(coordinates);
    }
}

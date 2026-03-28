package janggi.domain.point;

import java.util.Collections;
import java.util.List;

public class Route {

    private final List<Point> points;

    public Route(List<Point> points) {
        this.points = points;
    }

    public List<Point> getRoutes() {
        return Collections.unmodifiableList(points);
    }
}

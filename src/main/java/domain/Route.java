package domain;

import domain.unit.Point;
import java.util.Collections;
import java.util.List;

public class Route {
    private final List<Point> points;

    private Route(List<Point> points) {
        this.points = points;
    }

    public static Route of(List<Point> points) {
        return new Route(points);
    }

    public List<Point> getPoints() {
        return Collections.unmodifiableList(points);
    }
}

package janggi.domain.rule.route;

import janggi.domain.Location;
import java.util.ArrayList;
import java.util.List;

public class Route {

    private final List<Direction> directions;

    private Route(List<Direction> directions) {
        this.directions = directions;
    }

    public static Route from(List<Direction> directions) {
        return new Route(directions);
    }

    public static Route of(Direction direction, int count) {
        List<Direction> way = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            way.add(direction);
        }
        return new Route(way);
    }

    public List<Location> calculateLocationsOnPath(Location current) {
        List<Location> locationsOnPath = new ArrayList<>();
        for (Direction direction : directions) {
            current = direction.apply(current);
            locationsOnPath.add(current);
        }

        return locationsOnPath;
    }
}

package janggi.domain.direction;

import janggi.domain.Location;
import java.util.ArrayList;
import java.util.List;

public class Route {

    private final List<Direction> directions;

    private Route(List<Direction> directions) {
        this.directions = directions;
    }

    public static Route of(List<Direction> directions) {
        return new Route(directions);
    }

    public List<Location> apply(Location current) {
        List<Location> result = new ArrayList<>();
        for(Direction direction : directions) {
            current = direction.apply(current);
            result.add(current);
        }

        return result;
    }
}

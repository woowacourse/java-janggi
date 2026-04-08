package janggi.domain.rule.route;

import janggi.domain.board.Location;
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

    public static Route create(Direction direction, int count) {
        List<Direction> result = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            result.add(direction);
        }
        return new Route(result);
    }

    public List<Location> apply(Location current) {
        List<Location> result = new ArrayList<>();
        for (Direction direction : directions) {
            current = direction.apply(current);
            result.add(current);
        }

        return result;
    }
}

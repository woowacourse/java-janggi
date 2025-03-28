package domain.unit;

import domain.position.Position;
import domain.position.Route;
import java.util.ArrayList;
import java.util.List;

public class Movement {
    private final List<Direction> directions;

    private Movement(List<Direction> directions) {
        this.directions = directions;
    }

    public static Movement of(Direction... directions) {
        return new Movement(List.of(directions));
    }

    public Route calculateRouteBy(Position position) {
        List<Position> routes = new ArrayList<>();
        for (Direction direction : directions) {
            position = position.calculatePositionWithDirection(direction);
            routes.add(position);
        }
        return Route.of(routes);
    }
}

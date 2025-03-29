package domain.movement;

import domain.position.Position;
import domain.position.Route;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Movement {
    private final List<Direction> directions;

    private Movement(List<Direction> directions) {
        this.directions = directions;
    }

    public static Movement of(Direction... directions) {
        return new Movement(List.of(directions));
    }

    public static Movement of(Direction direction, int steps) {
        Direction[] directions = new Direction[steps];
        Arrays.fill(directions, direction);
        return Movement.of(directions);
    }

    public boolean canBeRoute(Position position) {
        for (Direction direction : directions) {
            if (!position.canBePosition(direction)) {
                return false;
            }
            position = position.calculatePositionWithDirection(direction);
        }
        return true;
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

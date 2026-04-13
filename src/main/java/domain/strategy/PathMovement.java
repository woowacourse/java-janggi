package domain.strategy;

import domain.Direction;
import domain.Position;

import java.util.ArrayList;
import java.util.List;

public class PathMovement extends MovementStrategy {

    @Override
    public List<Position> findRoute(List<List<Direction>> paths, Position sourcePosition) {
        List<Position> destinations = new ArrayList<>();
        for (List<Direction> path : paths) {
            List<Position> route = buildRoute(path, sourcePosition);
            if (!route.isEmpty()) {
                destinations.add(route.getLast());
            }
        }
        return destinations;
    }

    @Override
    protected List<Position> buildRoute(List<Direction> path, Position source) {
        List<Position> route = new ArrayList<>();
        Position current = source;

        for (Direction direction : path) {
            if (!current.canMove(direction.getX(), direction.getY())) {
                return List.of();
            }
            current = current.createPosition(direction.getX(), direction.getY());
            route.add(current);
        }
        return route;
    }
}

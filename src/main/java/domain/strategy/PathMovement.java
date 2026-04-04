package domain.strategy;

import domain.Direction;
import domain.Position;
import java.util.ArrayList;
import java.util.List;

public class PathMovement extends MovementStrategy {

    @Override
    protected List<Position> buildRoute(List<Direction> path, Position source, Position target) {
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

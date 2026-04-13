package domain.strategy;

import domain.Direction;
import domain.Position;

import java.util.ArrayList;
import java.util.List;

public class LinearMovement extends MovementStrategy {

    @Override
    protected List<Position> buildRoute(List<Direction> path, Position sourcePosition) {
        List<Position> route = new ArrayList<>();
        Direction direction = path.getFirst();
        Position current = sourcePosition;

        while (current.canMove(direction.getX(), direction.getY())) {
            Position next = current.createPosition(direction.getX(), direction.getY());
            route.add(next);
            current = next;
        }

        return List.copyOf(route);
    }
}

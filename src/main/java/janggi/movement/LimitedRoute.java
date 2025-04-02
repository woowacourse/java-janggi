package janggi.movement;

import janggi.position.Position;

import java.util.ArrayList;
import java.util.List;

public class LimitedRoute implements Route{

    private final List<Movement> movements;

    public LimitedRoute(List<Movement> movements) {
        this.movements = movements;
    }

    @Override
    public Position step(Position startPosition, Position arrivedPosition) {
        Position reachablePosition = startPosition;
        for (Movement movement : movements) {
            reachablePosition = movement.move(reachablePosition);
        }
        return reachablePosition;
    }


    public List<Position> extractPathPositions(Position startPosition ,Position arrivedPosition) {
        List<Position> pathPositions = new ArrayList<>();
        for (int i = 0; i < movements.size(); i++) {
            Position pathPosition = startPosition;
            for (int j = 0; j <= i; j++) {
                Movement movement = movements.get(j);
                pathPosition = movement.move(pathPosition);
            }
            pathPositions.add(pathPosition);
        }
        return pathPositions.stream()
                .filter(position -> !position.equals(arrivedPosition))
                .toList();
    }
}

package janggi.movement;

import janggi.position.Position;

import java.util.ArrayList;
import java.util.List;

public class LimitedMovement implements Movement {

    private final List<Direction> directions;

    public LimitedMovement(List<Direction> directions) {
        this.directions = directions;
    }

    @Override
    public Position step(Position startPosition, Position arrivedPosition) {
        Position reachablePosition = startPosition;
        for (Direction direction : directions) {
            reachablePosition = direction.move(reachablePosition);
        }
        return reachablePosition;
    }

    public List<Position> extractPathPositions(Position startPosition ,Position arrivedPosition) {
        List<Position> pathPositions = new ArrayList<>();
        for (int i = 0; i < directions.size(); i++) {
            Position pathPosition = startPosition;
            for (int j = 0; j <= i; j++) {
                Direction direction = directions.get(j);
                pathPosition = direction.move(pathPosition);
            }
            pathPositions.add(pathPosition);
        }
        return pathPositions.stream()
                .filter(position -> !position.equals(arrivedPosition))
                .toList();
    }
}

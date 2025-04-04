package janggi.movement;

import janggi.position.Position;

import java.util.ArrayList;
import java.util.List;

public class UnLimitedMovement implements Movement {

    private final List<Direction> directions;

    public UnLimitedMovement(List<Direction> directions) {
        this.directions = directions;
    }

    @Override
    public Position step(Position startPosition, Position arrivedPosition) {
        Position reachablePosition = startPosition;
        if (startPosition.isHorizontalFromPosition(arrivedPosition)) {
            for (Direction direction : directions) {
                reachablePosition = direction.move(reachablePosition);
                if (reachablePosition.isSameColumn(arrivedPosition)) {
                    return reachablePosition;
                }
            }
        }

        if (startPosition.isVerticalFromPosition(arrivedPosition)) {
            for (Direction direction : directions) {
                reachablePosition = direction.move(reachablePosition);
                if (reachablePosition.isSameRow(arrivedPosition)) {
                    return reachablePosition;
                }
            }
        }

        for (Direction direction : directions) {
            reachablePosition = direction.move(reachablePosition);
        }
        return reachablePosition;
    }

    public List<Position> extractPathPositions(Position startPosition ,Position arrivedPosition) {

        List<Position> pathPositions = new ArrayList<>();
        int arrivedValue = 0;
        if (startPosition.isHorizontalFromPosition(arrivedPosition)) {
            arrivedValue = startPosition.calculateColumnDistance(arrivedPosition);
        }
        if (startPosition.isVerticalFromPosition(arrivedPosition)) {
            arrivedValue = startPosition.calculateRowDistance(arrivedPosition);
        }
        if (startPosition.isCrossFromPosition(arrivedPosition)) {
            arrivedValue = startPosition.calculateRowDistance(arrivedPosition);
        }
        for (int i = 0; i < arrivedValue; i++) {
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

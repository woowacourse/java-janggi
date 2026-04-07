package domain.strategy;

import domain.coordinate.Direction;
import domain.coordinate.Position;

import java.util.ArrayList;
import java.util.List;

public class SlidingMoveStrategy implements MoveStrategy {

    private final List<Direction> directions;

    public SlidingMoveStrategy(List<Direction> directions) {
        this.directions = List.copyOf(directions);
    }

    @Override
    public List<List<Direction>> calculatePotentialPaths(Position start) {
        List<List<Direction>> paths = new ArrayList<>();

        addBasicPotentialPaths(start, paths);
        addPalaceEdgePaths(start, paths);
        addPalaceCenterPaths(start, paths);

        return paths;
    }

    private void addBasicPotentialPaths(Position start, List<List<Direction>> paths) {
        for (Direction direction : directions) {
            List<Direction> directionPath = new ArrayList<>();

            Position current = start;
            while (true) {
                current = current.nextPosition(direction);

                if (!current.isValidRange()) {
                    break;
                }
                directionPath.add(direction);
            }

            paths.add(directionPath);
        }
    }

    private void addPalaceEdgePaths(Position start, List<List<Direction>> paths) {
        if (start.isInPalaceEdgePosition()) {
            Direction palaceEdgeDirection = start.getPalaceEdgeDirection();
            List<Direction> directionPath = new ArrayList<>();

            for (int i = 0; i < 2; i++) {
                directionPath.add(palaceEdgeDirection);
            }

            paths.add(directionPath);
        }
    }

    private void addPalaceCenterPaths(Position start, List<List<Direction>> paths) {
        if (start.isInPalaceCenterPosition()) {
            List<Direction> palaceDirection = start.getPalaceCenterDirection();

            for (Direction direction : palaceDirection) {
                paths.add(List.of(direction));
            }
        }
    }
}

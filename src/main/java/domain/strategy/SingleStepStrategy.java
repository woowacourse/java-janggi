package domain.strategy;

import domain.coordinate.Direction;
import domain.coordinate.Position;

import java.util.ArrayList;
import java.util.List;

public class SingleStepStrategy implements MoveStrategy {

    private final List<Direction> directions;

    public SingleStepStrategy(List<Direction> directions) {
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
            Position dest = start.nextPosition(direction);

            if (!dest.isValidRange()) {
                continue;
            }

            directionPath.add(direction);
            paths.add(directionPath);
        }
    }

    private static void addPalaceEdgePaths(Position start, List<List<Direction>> paths) {
        if (start.isInPalaceEdgePosition()) {
            paths.add(List.of(start.getPalaceEdgeDirection()));
        }
    }

    private static void addPalaceCenterPaths(Position start, List<List<Direction>> paths) {
        if (start.isInPalaceCenterPosition()) {
            List<Direction> palaceDirection = start.getPalaceCenterDirection();

            for (Direction direction : palaceDirection) {
                paths.add(List.of(direction));
            }
        }
    }
}

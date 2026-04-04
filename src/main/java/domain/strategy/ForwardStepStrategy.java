package domain.strategy;

import domain.coordinate.Direction;
import domain.coordinate.Position;

import java.util.ArrayList;
import java.util.List;

public class ForwardStepStrategy implements MoveStrategy {

    private final List<Direction> directions;
    private final Direction forward;

    public ForwardStepStrategy(List<Direction> directions, Direction forward) {
        this.directions = List.copyOf(directions);
        this.forward = forward;
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

    private void addPalaceEdgePaths(Position start, List<List<Direction>> paths) {
        if (start.isInPalaceEdgePosition()) {
            Direction palaceEdgeDirection = start.getPalaceEdgeDirection();

            if (palaceEdgeDirection.isForward(forward)) {
                paths.add(List.of(palaceEdgeDirection));
            }
        }
    }

    private void addPalaceCenterPaths(Position start, List<List<Direction>> paths) {
        if (start.isInPalaceCenterPosition()) {
            List<Direction> palaceCenterDirection = start.getPalaceCenterDirection();

            for (Direction direction : palaceCenterDirection) {
                if (direction.isForward(forward)) {
                    paths.add(List.of(direction));
                }
            }
        }
    }
}

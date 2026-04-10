package domain.strategy;

import domain.coordinate.Direction;
import domain.coordinate.Position;

import java.util.ArrayList;
import java.util.List;

public abstract class MoveStrategy {

    public final List<List<Direction>> calculatePotentialPaths(Position start) {
        List<List<Direction>> paths = new ArrayList<>();

        addBasicPaths(start, paths);
        addPalaceEdgePaths(start, paths);
        addPalaceCenterPaths(start, paths);

        return paths;
    }

    protected abstract void addBasicPaths(Position start, List<List<Direction>> paths);

    private void addPalaceEdgePaths(Position start, List<List<Direction>> paths) {
        if (start.isInPalaceEdgePosition()) {
            Direction direction = start.getPalaceEdgeDirection();
            if (isAllowedDirection(direction)) {
                paths.add(getPalaceEdgeMovePath(direction));
            }
        }
    }

    private void addPalaceCenterPaths(Position start, List<List<Direction>> paths) {
        if (start.isInPalaceCenterPosition()) {
            for (Direction direction : start.getPalaceCenterDirection()) {
                if (isAllowedDirection(direction)) {
                    paths.add(getPalaceCenterMovePath(direction));
                }
            }
        }
    }

    protected boolean isAllowedDirection(Direction direction) {
        return false;
    }

    protected List<Direction> getPalaceEdgeMovePath(Direction direction) {
        return List.of(direction);
    }

    protected List<Direction> getPalaceCenterMovePath(Direction direction) {
        return List.of(direction);
    }
}

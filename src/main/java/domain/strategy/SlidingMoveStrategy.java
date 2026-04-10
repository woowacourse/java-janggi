package domain.strategy;

import domain.coordinate.Direction;
import domain.coordinate.Position;

import java.util.ArrayList;
import java.util.List;

public class SlidingMoveStrategy extends MoveStrategy {

    private final List<Direction> directions;

    public SlidingMoveStrategy(List<Direction> directions) {
        this.directions = List.copyOf(directions);
    }

    @Override
    protected void addBasicPaths(Position start, List<List<Direction>> paths) {
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

    @Override
    protected boolean isAllowedDirection(Direction direction) {
        return true;
    }

    @Override
    protected List<Direction> getPalaceEdgeMovePath(Direction direction) {
        return List.of(direction, direction);
    }
}

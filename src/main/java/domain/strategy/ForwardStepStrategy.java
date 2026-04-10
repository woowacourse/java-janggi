package domain.strategy;

import domain.coordinate.Direction;
import domain.coordinate.Position;

import java.util.ArrayList;
import java.util.List;

public class ForwardStepStrategy extends MoveStrategy {

    private final List<Direction> directions;
    private final Direction forward;

    public ForwardStepStrategy(List<Direction> directions, Direction forward) {
        this.directions = List.copyOf(directions);
        this.forward = forward;
    }

    @Override
    protected void addBasicPaths(Position start, List<List<Direction>> paths) {
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

    @Override
    protected boolean isAllowedDirection(Direction direction) {
        return direction.isForward(forward);
    }
}

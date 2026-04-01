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

        for (Direction direction : directions) {
            List<Direction> directionPath = new ArrayList<>();
            Position dest = start.nextPosition(direction);

            if (!dest.isValidRange()) {
                continue;
            }

            directionPath.add(direction);
            paths.add(directionPath);
        }

        return List.copyOf(paths);
    }
}

package domain.strategy;

import domain.Position;
import java.util.ArrayList;
import java.util.List;

public class OneStepStrategy implements MovementStrategy {
    private final List<Direction> directions;

    public OneStepStrategy(List<Direction> directions) {
        this.directions = directions;
    }

    @Override
    public List<Path> generatePaths(Position current) {
        List<Path> paths = new ArrayList<>();
        for (Direction direction : directions) {
            if (current.canMove(direction)) {
                paths.add(new Path(List.of(current.move(direction))));
            }
        }
        return paths;
    }
}

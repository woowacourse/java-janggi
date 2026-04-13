package domain.strategy;

import domain.game.Position;
import java.util.List;

public class OneStepStrategy implements MovementStrategy {
    private final List<Direction> directions;

    public OneStepStrategy(List<Direction> directions) {
        this.directions = directions;
    }

    @Override
    public List<Path> generatePaths(Position current) {
        return directions.stream()
                .filter(current::canMove)
                .map(direction -> new Path(List.of(current.move(direction))))
                .toList();
    }
}

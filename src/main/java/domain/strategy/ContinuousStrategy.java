package domain.strategy;

import domain.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ContinuousStrategy implements MovementStrategy {
    private final List<Direction> directions;

    public ContinuousStrategy(List<Direction> directions) {
        this.directions = directions;
    }

    @Override
    public List<Path> generatePaths(Position current) {
        return directions.stream()
                .filter(current::canMove)
                .map(direction -> createPath(current, direction))
                .toList();
    }

    private Path createPath(Position current, Direction direction) {
        List<Position> positions = new ArrayList<>();
        Position position = current;

        while (position.canMove(direction)) {
            position = position.move(direction);
            positions.add(position);
        }
        return new Path(positions);
    }
}

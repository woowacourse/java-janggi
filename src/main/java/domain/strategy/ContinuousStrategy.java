package domain.strategy;

import domain.game.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ContinuousStrategy implements MovementStrategy {
    private final List<Direction> directions;

    public ContinuousStrategy(List<Direction> directions) {
        this.directions = directions;
    }

    @Override
    public List<Path> generatePaths(Position current) {
        return directions.stream()
                .map(direction -> createPath(current, direction))
                .flatMap(Optional::stream)
                .toList();
    }

    private Optional<Path> createPath(Position current, Direction direction) {
        List<Position> positions = new ArrayList<>();
        Position position = current;

        while (position.canMove(direction)) {
            position = position.move(direction);
            positions.add(position);
        }
        if (positions.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new Path(positions));
    }
}

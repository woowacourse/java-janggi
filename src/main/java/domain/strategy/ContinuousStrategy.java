package domain.strategy;

import domain.Position;
import java.util.ArrayList;
import java.util.List;

public class ContinuousStrategy implements MovementStrategy {
    private final List<Direction> directions;

    public ContinuousStrategy(List<Direction> directions) {
        this.directions = directions;
    }

    @Override
    public List<Path> generatePaths(Position current) {
        List<Path> paths = new ArrayList<>();
        for (Direction direction : directions) {
            Path path = createPath(current, direction);
            if (!path.isEmpty()) {
                paths.add(path);
            }
        }
        return paths;
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

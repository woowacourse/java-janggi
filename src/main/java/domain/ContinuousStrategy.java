package domain;

import java.util.ArrayList;
import java.util.List;

public class ContinuousStrategy implements MovementStrategy {
    private final List<Direction> directions;

    public ContinuousStrategy(List<Direction> directions) {
        this.directions = directions;
    }

    @Override
    public List<Path> generatePaths(Position current, BoardReader board) {
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
        Position pos = current;

        while (pos.canMove(direction)) {
            pos = pos.move(direction);
            positions.add(pos);
        }
        return new Path(positions);
    }
}

package domain.strategy;

import domain.Position;
import java.util.ArrayList;
import java.util.List;

public class SequenceStrategy implements MovementStrategy {
    private final List<List<Direction>> sequences;

    public SequenceStrategy(List<List<Direction>> sequences) {
        this.sequences = sequences;
    }

    @Override
    public List<Path> generatePaths(Position current) {
        List<Path> paths = new ArrayList<>();
        for (List<Direction> sequence : sequences) {
            Path path = createPath(current, sequence);
            if (!path.isEmpty()) {
                paths.add(path);
            }
        }
        return paths;
    }

    private Path createPath(Position current, List<Direction> sequence) {
        List<Position> positions = new ArrayList<>();
        Position pos = current;

        for (Direction direction : sequence) {
            if (!pos.canMove(direction)) {
                return new Path(List.of());
            }
            pos = pos.move(direction);
            positions.add(pos);
        }
        return new Path(positions);
    }
}

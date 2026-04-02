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
        return sequences.stream()
                .filter(current::canMove)
                .map(sequence -> createPath(current, sequence))
                .toList();
    }

    private Path createPath(Position current, List<Direction> sequence) {
        List<Position> positions = new ArrayList<>();
        Position position = current;
        for (Direction direction : sequence) {
            position = position.move(direction);
            positions.add(position);
        }
        return new Path(positions);
    }
}

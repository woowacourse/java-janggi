package domain.strategy;

import domain.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SequenceStrategy implements MovementStrategy {
    private final List<List<Direction>> sequences;

    public SequenceStrategy(List<List<Direction>> sequences) {
        this.sequences = sequences;
    }

    @Override
    public List<Path> generatePaths(Position current) {
        return sequences.stream()
                .map(sequence -> createPath(current, sequence))
                .filter(path -> !path.isEmpty())
                .toList();
    }

    private Path createPath(Position current, List<Direction> sequence) {
        List<Position> positions = new ArrayList<>();
        Position position = current;
        int index = 0;
        while (index < sequence.size() && position.canMove(sequence.get(index))) {
            position = position.move(sequence.get(index));
            positions.add(position);
            index++;
        }
        if (isInvalidPath(sequence, index)) {
            return new Path(List.of());
        }
        return new Path(positions);
    }

    private boolean isInvalidPath(List<Direction> sequence, int index) {
        return index < sequence.size();
    }
}

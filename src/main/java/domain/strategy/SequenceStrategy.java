package domain.strategy;

import domain.coordinate.Direction;
import domain.coordinate.DirectionSequence;
import domain.coordinate.Path;
import domain.coordinate.Position;
import domain.coordinate.Topology;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SequenceStrategy implements Strategy {

    private final List<DirectionSequence> sequences;

    public SequenceStrategy(List<DirectionSequence> sequences) {
        this.sequences = sequences;
    }

    @Override
    public List<Path> getPaths(Position start, Topology topology) {
        List<Path> paths = new ArrayList<>();
        for (DirectionSequence sequence : sequences) {
            Path path = buildPath(start, sequence);
            if (path != null) {
                paths.add(path);
            }
        }
        return paths;
    }

    private Path buildPath(Position start, DirectionSequence sequence) {
        List<Position> positions = new ArrayList<>();
        Position current = start;
        for (Direction direction : sequence.directions()) {
            Optional<Position> next = current.tryNextPosition(direction);
            if (next.isEmpty()) {
                return null;
            }
            current = next.get();
            positions.add(current);
        }
        return new Path(positions);
    }
}

package domain.strategy;

import domain.board.BoardBounds;
import domain.coordinate.Direction;
import domain.coordinate.DirectionSequence;
import domain.coordinate.Path;
import domain.coordinate.Position;

import java.util.ArrayList;
import java.util.List;

public class SequenceStrategy implements Strategy {

    private final List<DirectionSequence> sequences;

    public SequenceStrategy(List<DirectionSequence> sequences) {
        this.sequences = sequences;
    }

    @Override
    public List<Path> getPaths(Position start, BoardBounds bounds) {
        List<Path> paths = new ArrayList<>();
        for (DirectionSequence sequence : sequences) {
            Path path = buildPath(start, sequence, bounds);
            if (path != null) {
                paths.add(path);
            }
        }
        return paths;
    }

    private Path buildPath(Position start, DirectionSequence sequence, BoardBounds bounds) {
        List<Position> positions = new ArrayList<>();
        Position current = start;
        for (Direction direction : sequence.directions()) {
            current = current.nextPosition(direction);
            if (!bounds.contains(current)) {
                return null;
            }
            positions.add(current);
        }
        return new Path(positions);
    }
}

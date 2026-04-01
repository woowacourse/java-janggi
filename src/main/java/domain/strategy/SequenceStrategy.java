package domain.strategy;

import domain.board.BoardBounds;
import domain.coordinate.Direction;
import domain.coordinate.Path;
import domain.coordinate.Position;

import java.util.ArrayList;
import java.util.List;

public class SequenceStrategy implements Strategy {

    private final List<List<Direction>> sequences;

    public SequenceStrategy(List<List<Direction>> sequences) {
        this.sequences = sequences;
    }

    @Override
    public List<Path> getPaths(Position start, BoardBounds bounds) {
        List<Path> paths = new ArrayList<>();
        for (List<Direction> sequence : sequences) {
            Path path = buildPath(start, sequence, bounds);
            if (path != null) {
                paths.add(path);
            }
        }
        return paths;
    }

    private Path buildPath(Position start, List<Direction> sequence, BoardBounds bounds) {
        List<Position> positions = new ArrayList<>();
        Position current = start;
        for (Direction direction : sequence) {
            current = current.nextPosition(direction);
            if (!bounds.contains(current)) {
                return null;
            }
            positions.add(current);
        }
        return new Path(positions);
    }
}

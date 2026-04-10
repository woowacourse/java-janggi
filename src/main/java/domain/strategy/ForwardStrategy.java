package domain.strategy;

import domain.coordinate.Direction;
import domain.coordinate.Path;
import domain.coordinate.Position;
import domain.coordinate.Topology;

import java.util.List;

public class ForwardStrategy implements Strategy {

    private final Strategy delegate;
    private final Direction forward;

    public ForwardStrategy(Strategy delegate, Direction forward) {
        this.delegate = delegate;
        this.forward = forward;
    }

    @Override
    public List<Path> getPaths(Position start, Topology topology) {
        return delegate.getPaths(start, topology).stream()
                .filter(path -> !isBackward(path, start))
                .toList();
    }

    private boolean isBackward(Path path, Position start) {
        Position destination = path.getPositions().getFirst();
        int rowDiff = destination.row() - start.row();
        return rowDiff != 0 && rowDiff != forward.getRow();
    }
}
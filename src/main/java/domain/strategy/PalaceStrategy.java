package domain.strategy;

import domain.coordinate.Path;
import domain.coordinate.Position;
import domain.coordinate.Topology;
import java.util.List;

public class PalaceStrategy implements Strategy {

    private final Strategy delegate;

    public PalaceStrategy(Strategy delegate) {
        this.delegate = delegate;
    }

    @Override
    public List<Path> getPaths(Position start, Topology topology) {
        return delegate.getPaths(start, topology).stream()
                .filter(path -> path.getPositions().stream().allMatch(Position::isInPalace))
                .toList();
    }
}

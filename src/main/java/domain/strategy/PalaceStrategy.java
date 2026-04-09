package domain.strategy;

import domain.coordinate.Path;
import domain.coordinate.Position;
import java.util.List;

public class PalaceStrategy implements Strategy {

    private final Strategy delegate;

    public PalaceStrategy(Strategy delegate) {
        this.delegate = delegate;
    }

    @Override
    public List<Path> getPaths(Position start) {
        return delegate.getPaths(start).stream()
                .filter(path -> path.getPositions().stream().allMatch(Position::isInPalace))
                .toList();
    }
}

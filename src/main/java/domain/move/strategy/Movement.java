package domain.move.strategy;

import domain.board.Intersection;
import domain.game.Side;
import domain.move.Path;
import java.util.List;

public abstract class Movement {

    public final List<Path> movablePaths(Intersection from, Side side) {
        List<Path> allPaths = candidatePaths(from, side);

        return allPaths.stream()
                .filter(Path::isInBoundsDestination)
                .toList();
    }

    protected abstract List<Path> candidatePaths(Intersection from, Side side);
}

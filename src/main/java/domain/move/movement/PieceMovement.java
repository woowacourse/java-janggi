package domain.move.movement;

import domain.board.Intersection;
import domain.game.Side;
import java.util.List;

public abstract class PieceMovement {

    public final List<Path> movablePaths(Intersection from, Side side) {
        List<Path> allPaths = candidatePaths(from, side);

        return allPaths.stream()
                .filter(Path::isInBoundsDestination)
                .toList();
    }

    protected abstract List<Path> candidatePaths(Intersection from, Side side);
}

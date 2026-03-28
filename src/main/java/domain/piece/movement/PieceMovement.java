package domain.piece.movement;

import domain.board.Intersection;
import domain.game.Side;
import java.util.List;

public abstract class PieceMovement {

    public final List<Intersection> movableIntersections(Intersection from, Side side) {
        List<Intersection> allIntersections = candidateIntersections(from, side);

        return allIntersections.stream()
                .filter(Intersection::isInBounds)
                .toList();
    }

    protected abstract List<Intersection> candidateIntersections(Intersection from, Side side);
}

package janggi.domain.movement;

import janggi.domain.Coordinate;
import janggi.domain.board.PieceSearcher;
import janggi.domain.movestep.MoveStep;
import java.util.Set;

public final class OnceMovement implements Movement {

    private final Set<MoveStep> moveSteps;

    public OnceMovement(MoveStep... moveSteps) {
        this.moveSteps = Set.of(moveSteps);
    }

    @Override
    public boolean canMove(
        final Coordinate departure,
        final Coordinate arrival,
        final PieceSearcher pieceSearcher
    ) {
        return moveSteps.stream()
            .filter(departure::canMove)
            .map(departure::move)
            .anyMatch(arrival::equals);
    }
}

package domain.movement.pathless;

import domain.Coordinate;
import domain.movement.MoveVector;
import domain.board.PieceSearcher;
import domain.movement.Movement;
import java.util.Set;

public abstract class PathlessMovement extends Movement {

    public PathlessMovement(final Set<MoveVector> moveVectors) {
        super(moveVectors);
    }

    @Override
    public boolean canMove(
        final Coordinate departure,
        final Coordinate arrival,
        final PieceSearcher pieceSearcher
    ) {
        return moveVectorsAt(departure).stream()
            .map(MoveVector::first)
            .filter(departure::canMove)
            .map(departure::move)
            .anyMatch(arrival::equals);
    }
}

package domain.piece.noPathMovement;

import domain.Coordinate;
import domain.MoveVector;
import domain.board.PieceSearcher;
import domain.piece.Movement;
import java.util.Set;

public abstract class NoPathMovement extends Movement {

    public NoPathMovement(final Set<MoveVector> moveVectors) {
        super(moveVectors);
    }

    @Override
    public boolean canMove(
        final Coordinate departure,
        final Coordinate arrival,
        final PieceSearcher pieceSearcher
    ) {
        return moveVectors().stream()
            .filter(departure::canMove)
            .map(departure::move)
            .anyMatch(arrival::equals);
    }
}

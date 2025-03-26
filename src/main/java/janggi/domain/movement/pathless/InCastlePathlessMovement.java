package janggi.domain.movement.pathless;

import janggi.domain.Coordinate;
import janggi.domain.movement.MoveVector;
import janggi.domain.board.PieceSearcher;
import java.util.Set;

public abstract class InCastlePathlessMovement extends PathlessMovement {

    public InCastlePathlessMovement(final Set<MoveVector> moveVectors) {
        super(moveVectors);
    }

    @Override
    public boolean canMove(
        final Coordinate departure,
        final Coordinate arrival,
        final PieceSearcher pieceSearcher
    ) {
        return arrival.isInCastle() && super.canMove(departure, arrival, pieceSearcher);
    }
}

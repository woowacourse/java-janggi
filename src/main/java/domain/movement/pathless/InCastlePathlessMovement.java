package domain.movement.pathless;

import domain.Coordinate;
import domain.movement.MoveVector;
import domain.board.PieceSearcher;
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

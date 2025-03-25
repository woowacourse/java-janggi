package domain.piece.noPathMovement;

import domain.Coordinate;
import domain.MoveVector;
import domain.board.PieceSearcher;
import java.util.Set;

public abstract class InCastleNoPathMovement extends NoPathMovement {

    public InCastleNoPathMovement(final Set<MoveVector> moveVectors) {
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

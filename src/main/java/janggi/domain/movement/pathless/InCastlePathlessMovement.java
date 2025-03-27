package janggi.domain.movement.pathless;

import janggi.domain.Coordinate;
import janggi.domain.movement.MoveProcess;
import janggi.domain.board.PieceSearcher;
import java.util.Set;

public abstract class InCastlePathlessMovement extends PathlessMovement {

    public InCastlePathlessMovement(final Set<MoveProcess> moveProcesses) {
        super(moveProcesses);
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

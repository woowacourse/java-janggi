package janggi.domain.movement.pathless;

import janggi.domain.Coordinate;
import janggi.domain.board.PieceSearcher;
import janggi.domain.movement.MoveProcess;
import janggi.domain.movement.MoveStep;
import java.util.Set;

public class InCastleCrossMovement extends PathlessMovement {

    public InCastleCrossMovement() {
        super(Set.of(
            new MoveProcess(MoveStep.UP),
            new MoveProcess(MoveStep.DOWN),
            new MoveProcess(MoveStep.LEFT),
            new MoveProcess(MoveStep.RIGHT)
        ));
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

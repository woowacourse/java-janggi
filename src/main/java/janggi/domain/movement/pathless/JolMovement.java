package janggi.domain.movement.pathless;

import janggi.domain.Coordinate;
import janggi.domain.board.PieceSearcher;
import janggi.domain.movement.MoveStep;
import janggi.domain.movement.MoveProcess;
import java.util.Iterator;
import java.util.Set;

public class JolMovement extends PathlessMovement {

    public JolMovement() {
        super(Set.of(
            new MoveProcess(MoveStep.LEFT),
            new MoveProcess(MoveStep.RIGHT),
            new MoveProcess(MoveStep.UP)
        ));
    }

    @Override
    public boolean canMove(
        final Coordinate departure,
        final Coordinate arrival,
        final PieceSearcher pieceSearcher
    ) {
        return moveProcessesAt(departure).stream()
            .map(MoveProcess::iterator)
            .map(Iterator::next)
            .filter(MoveStep::isUpDirection)
            .filter(departure::canMove)
            .map(departure::move)
            .anyMatch(arrival::equals);
    }
}

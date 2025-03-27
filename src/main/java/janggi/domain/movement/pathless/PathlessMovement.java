package janggi.domain.movement.pathless;

import janggi.domain.Coordinate;
import janggi.domain.movement.MoveProcess;
import janggi.domain.board.PieceSearcher;
import janggi.domain.movement.Movement;
import java.util.Iterator;
import java.util.Set;

public abstract class PathlessMovement extends Movement {

    public PathlessMovement(final Set<MoveProcess> moveProcesses) {
        super(moveProcesses);
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
            .filter(departure::canMove)
            .map(departure::move)
            .anyMatch(arrival::equals);
    }
}

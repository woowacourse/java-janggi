package janggi.domain.movement;

import janggi.domain.Coordinate;
import janggi.domain.board.PieceSearcher;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class Movement {

    private final Set<MoveProcess> moveProcesses;

    public Movement(Set<MoveProcess> moveProcesses) {
        this.moveProcesses = moveProcesses;
    }

    public abstract boolean canMove(Coordinate departure, Coordinate arrival, PieceSearcher pieceSearcher);

    protected final Set<MoveProcess> moveProcessesAt(Coordinate coordinate) {
        if (coordinate.isInCastle()) {
            return addDiagonalMoveProcessesAt(coordinate);
        }
        return moveProcesses;
    }

    private Set<MoveProcess> addDiagonalMoveProcessesAt(final Coordinate coordinate) {
        final var connections = coordinate.findCastleConnections();
        final var moveProcesses = connections.stream()
            .map(coordinate::computeMoveUnitToArrival)
            .map(moveUnit -> new MoveProcess(moveUnit, moveUnit))
            .collect(Collectors.toSet());

        moveProcesses.addAll(this.moveProcesses);
        return moveProcesses;
    }
}

package janggi.domain.movement;

import janggi.domain.Coordinate;
import janggi.domain.board.PieceSearcher;
import janggi.domain.movestep.MoveProcess;
import java.util.ArrayList;
import java.util.Set;

public class SeveralMovement implements Movement {

    private final Set<MoveProcess> moveProcesses;

    public SeveralMovement(final MoveProcess... moveProcesses) {
        this.moveProcesses = Set.of(moveProcesses);
    }

    @Override
    public boolean canMove(
        final Coordinate departure,
        final Coordinate arrival,
        final PieceSearcher pieceSearcher
    ) {
        final var path = findPath(departure, arrival);
        final var coordinates = path.coordinates();

        return path.isReachable() && pieceSearcher.nonePiecesIn(coordinates);
    }

    protected final Path findPath(final Coordinate departure, final Coordinate arrival) {
        for (final var moveProcess : moveProcesses) {
            final var path = computePath(departure, arrival, moveProcess);

            if (path.isReachable()) {
                return path;
            }
        }
        return Path.unreachable();
    }

    private static Path computePath(
        final Coordinate departure,
        final Coordinate arrival,
        final MoveProcess moveProcess
    ) {
        var current = departure;
        final var coordinates = new ArrayList<Coordinate>();

        for (final var moveStep : moveProcess) {
            if (!current.canMove(moveStep)) {
                break;
            }
            current = current.move(moveStep);

            if (current.equals(arrival)) {
                return new Path(coordinates);
            }
            coordinates.add(current);
        }

        return Path.unreachable();
    }
}

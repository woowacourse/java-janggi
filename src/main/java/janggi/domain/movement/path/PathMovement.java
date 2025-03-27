package janggi.domain.movement.path;

import janggi.domain.Coordinate;
import janggi.domain.movement.MoveProcess;
import janggi.domain.board.PieceSearcher;
import janggi.domain.movement.Movement;
import java.util.ArrayList;
import java.util.Set;

public abstract class PathMovement extends Movement {

    public PathMovement(final Set<MoveProcess> moveProcesses) {
        super(moveProcesses);
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
        for (final var moveProcess : moveProcessesAt(departure)) {
            var current = departure;
            final var path = new ArrayList<Coordinate>();

            for (final var moveStep : moveProcess) {
                if (current.canMove(moveStep)) {
                    var next = current.move(moveStep);
                    if (next.equals(arrival)) {
                        return new Path(path);
                    }

                    path.add(next);
                    current = next;
                } else {
                    break;
                }
            }
        }
        return Path.unreachable();
    }
}

package janggi.domain.movement.path;

import janggi.domain.Coordinate;
import janggi.domain.movement.MoveVector;
import janggi.domain.board.PieceSearcher;
import janggi.domain.movement.Movement;
import java.util.ArrayList;
import java.util.Set;

public abstract class PathMovement extends Movement {

    public PathMovement(final Set<MoveVector> moveVectors) {
        super(moveVectors);
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
        for (final var moveVector : moveVectorsAt(departure)) {
            var current = departure;
            final var path = new ArrayList<Coordinate>();

            for (final var moveUnit : moveVector) {
                if (current.canMove(moveUnit)) {
                    var next = current.move(moveUnit);
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

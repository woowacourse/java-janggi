package domain.piece.pathMovement;

import domain.Coordinate;
import domain.MoveVector;
import java.util.ArrayList;
import java.util.Set;

public abstract class UnlimitedPathMovement extends PathMovement {

    public UnlimitedPathMovement(final Set<MoveVector> moveVectors) {
        super(moveVectors);
    }

    protected final Path findPath(final Coordinate departure, final Coordinate arrival) {
        for (final var movement : moveVectors()) {
            var current = departure;
            final var path = new ArrayList<Coordinate>();

            while (current.canMove(movement)) {
                var next = current.move(movement);
                if (next.equals(arrival)) {
                    return new Path(path);
                }

                path.add(next);
                current = next;
            }
        }
        return Path.unreachable();
    }
}

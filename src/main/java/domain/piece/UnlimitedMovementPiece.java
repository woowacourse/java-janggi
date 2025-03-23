package domain.piece;

import domain.Coordinate;
import domain.Movement;
import domain.Team;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class UnlimitedMovementPiece extends Piece {

    public UnlimitedMovementPiece(final Team team, final Set<Movement> movements) {
        super(team, movements);
    }

    @Override
    protected final Set<Coordinate> findMovableCandidates(Coordinate departure) {
        Set<Coordinate> candidates = new HashSet<>();

        for (final var movement : this.movements) {
            var current = departure;

            while (current.canMove(movement)) {
                var next = current.move(movement);
                candidates.add(next);
                current = next;
            }
        }
        return candidates;
    }

    @Override
    protected abstract List<Coordinate> findPaths(final Coordinate departure, final Coordinate arrival);
}

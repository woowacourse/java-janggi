package domain.piece;

import domain.Coordinate;
import domain.Movement;
import domain.Team;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class LimitedMovementPiece extends Piece {

    public LimitedMovementPiece(final Team team, final Set<Movement> movements) {
        super(team, movements);
    }

    @Override
    protected final Set<Coordinate> findMovableCandidates(final Coordinate departure) {
        return movements.stream()
            .filter(departure::canMove)
            .map(departure::move)
            .collect(Collectors.toSet());
    }

    @Override
    protected abstract List<Coordinate> findPaths(final Coordinate departure, final Coordinate arrival);
}

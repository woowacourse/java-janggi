package janggi.domain.movement;

import janggi.domain.Coordinate;
import janggi.domain.board.PieceSearcher;
import java.util.Set;

public final class CastleBounded implements Movement {

    public static final Set<Integer> CASTLE_X_COORDINATES = Set.of(4, 5, 6);
    public static final Set<Integer> CASTLE_Y_COORDINATES = Set.of(1, 2, 3, 8, 9, 10);

    private final Movement movement;

    public CastleBounded(final Movement movement) {
        this.movement = movement;
    }

    @Override
    public boolean canMove(
        final Coordinate departure,
        final Coordinate arrival,
        final PieceSearcher pieceSearcher
    ) {
        return isInCastle(departure)
            && isInCastle(arrival)
            && movement.canMove(departure, arrival, pieceSearcher);
    }

    private boolean isInCastle(Coordinate coordinate) {
        return CASTLE_X_COORDINATES.contains(coordinate.x())
            && CASTLE_Y_COORDINATES.contains(coordinate.y());
    }
}

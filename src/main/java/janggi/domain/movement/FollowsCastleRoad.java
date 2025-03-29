package janggi.domain.movement;

import janggi.domain.Coordinate;
import janggi.domain.board.PieceSearcher;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class FollowsCastleRoad implements Movement {

    private static final Map<Coordinate, Set<Coordinate>> CONNECTIONS = Map.of(
        new Coordinate(4, 1), Set.of(new Coordinate(5, 2), new Coordinate(6, 3)),
        new Coordinate(4, 3), Set.of(new Coordinate(5, 2), new Coordinate(6, 1)),
        new Coordinate(6, 1), Set.of(new Coordinate(5, 2), new Coordinate(4, 3)),
        new Coordinate(6, 3), Set.of(new Coordinate(5, 2), new Coordinate(4, 1)),
        new Coordinate(5, 2),
        Set.of(new Coordinate(4, 1), new Coordinate(4, 3), new Coordinate(6, 1),
            new Coordinate(6, 3)),

        new Coordinate(4, 8), Set.of(new Coordinate(5, 9), new Coordinate(6, 10)),
        new Coordinate(4, 10), Set.of(new Coordinate(5, 9), new Coordinate(6, 8)),
        new Coordinate(6, 8), Set.of(new Coordinate(5, 9), new Coordinate(4, 10)),
        new Coordinate(6, 10), Set.of(new Coordinate(5, 9), new Coordinate(4, 8)),
        new Coordinate(5, 9),
        Set.of(new Coordinate(4, 8), new Coordinate(4, 10), new Coordinate(6, 8),
            new Coordinate(6, 10))
    );

    private final Movement movement;

    public FollowsCastleRoad(final Movement movement) {
        this.movement = movement;
    }

    @Override
    public boolean canMove(
        final Coordinate departure,
        final Coordinate arrival,
        final PieceSearcher pieceSearcher
    ) {
        final var connected = CONNECTIONS.getOrDefault(departure, Collections.emptySet());

        return connected.contains(arrival)
            && movement.canMove(departure, arrival, pieceSearcher);
    }
}

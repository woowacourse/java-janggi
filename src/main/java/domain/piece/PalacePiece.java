package domain.piece;

import domain.board.Intersection;
import domain.game.Side;
import domain.movement.MoveAmount;
import domain.movement.Route;
import domain.movement.strategy.MoveStrategy;
import domain.movement.strategy.Straight;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public abstract class PalacePiece extends StaticPositionedPiece {

    private static final MoveAmount MAX_MOVE_DISTANCE = new MoveAmount(1);

    private final MoveStrategy moveStrategy = new Straight(MAX_MOVE_DISTANCE);

    public PalacePiece(Side side) {
        super(side);
    }

    @Override
    public final List<Intersection> movableIntersections(
            Intersection from,
            AlivePieces alivePieces
    ) {
        Stream<Intersection> cardinalDestinations = findReachableDestinations(
                moveStrategy.getCardinalRoutes(from),
                alivePieces
        );
        Stream<Intersection> palaceDestinations = findReachableDestinations(
                moveStrategy.getPalaceRoutes(from),
                alivePieces
        );

        return Stream.concat(cardinalDestinations, palaceDestinations)
                .toList();
    }

    private Stream<Intersection> findReachableDestinations(
            Collection<Route> routes,
            AlivePieces alivePieces
    ) {
        return routes.stream()
                .filter(Route::containsOnlyPalace)
                .filter(route -> route.canReachDestinationThroughPath(alivePieces, side))
                .map(Route::getDestination);
    }
}

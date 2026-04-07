package domain.piece;

import domain.board.Intersection;
import domain.game.Side;
import domain.movement.MoveAmount;
import domain.movement.Route;
import domain.movement.Vector;
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
        List<Vector> cardinals = Vector.cardinals();
        List<Vector> palaceDiagonals = from.getPalaceDiagonalVectors();

        List<Vector> movableVectors = Stream.concat(cardinals.stream(), palaceDiagonals.stream())
                .toList();

        return findReachableDestinations(from, movableVectors, alivePieces);
    }

    private List<Intersection> findReachableDestinations(
            Intersection from,
            Collection<Vector> vectors,
            AlivePieces alivePieces
    ) {
        return findValidRoute(from, vectors, alivePieces)
                .filter(Route::containsOnlyPalace)
                .map(Route::getDestination)
                .toList();
    }

    private Stream<Route> findValidRoute(
            Intersection from,
            Collection<Vector> vectors,
            AlivePieces alivePieces
    ) {
        return moveStrategy.getRoutes(from, vectors)
                .stream()
                .filter(route -> route.canReachDestinationThroughPath(alivePieces, side));
    }
}

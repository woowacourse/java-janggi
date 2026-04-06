package domain.piece;

import domain.board.Intersection;
import domain.game.Side;
import domain.movement.MoveAmount;
import domain.movement.Route;
import domain.movement.Vector;
import domain.movement.strategy.MoveStrategy;
import domain.movement.strategy.Straight;
import java.util.List;
import java.util.stream.Stream;

public abstract class PalacePiece extends StaticPositionedPiece {

    private static final MoveAmount MAX_MOVE_DISTANCE = new MoveAmount(1);

    private final MoveStrategy moveStrategy = new Straight(MAX_MOVE_DISTANCE);

    public PalacePiece(Side side) {
        super(side);
    }

    @Override
    public final boolean canMove(
            Intersection from,
            Intersection to,
            AlivePieces alivePieces
    ) {
        return movableIntersections(from, alivePieces)
                .contains(to);
    }

    @Override
    public final List<Intersection> movableIntersections(
            Intersection from,
            AlivePieces alivePieces
    ) {
        Stream<Intersection> cardinalDestinations = Vector.cardinals()
                .stream()
                .flatMap(vector -> findReachableDestinations(from, vector, alivePieces));
        Stream<Intersection> diagonalDestinations = from.getPalaceDiagonalVectors()
                .stream()
                .flatMap(vector -> findReachableDestinations(from, vector, alivePieces));

        return Stream.concat(cardinalDestinations, diagonalDestinations)
                .toList();
    }

    private Stream<Intersection> findReachableDestinations(
            Intersection from,
            Vector vector,
            AlivePieces alivePieces
    ) {
        return findValidRoute(from, vector, alivePieces)
                .filter(Route::containsOnlyPalace)
                .map(Route::getDestination);
    }

    private Stream<Route> findValidRoute(
            Intersection from,
            Vector vector,
            AlivePieces alivePieces
    ) {
        return moveStrategy.getRoutes(from, vector)
                .stream()
                .filter(route -> route.canReachDestinationThroughPath(alivePieces, side));
    }
}

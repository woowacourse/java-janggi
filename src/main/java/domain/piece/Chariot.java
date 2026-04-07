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

public final class Chariot extends StaticPositionedPiece {

    private static final int FAR_FROM_BASE_ROW = 0;
    private static final List<Integer> INITIAL_FILES = List.of(1, 9);
    private static final MoveAmount MAX_MOVE_DISTANCE = MoveAmount.maximum();

    private final MoveStrategy moveStrategy = new Straight(MAX_MOVE_DISTANCE);

    public Chariot(Side side) {
        super(side);
    }

    @Override
    public List<Intersection> initAt() {
        int row = side.calculateRowFromBase(FAR_FROM_BASE_ROW);

        return INITIAL_FILES.stream()
                .map(file -> new Intersection(row, file))
                .toList();
    }

    @Override
    public List<Intersection> movableIntersections(
            Intersection from,
            AlivePieces alivePieces
    ) {
        Stream<Intersection> cardinalDestinations = findCardinalDestinations(
                from,
                alivePieces
        );
        Stream<Intersection> palaceDestinations = findPalaceDestinations(
                from,
                alivePieces
        );

        return Stream.concat(cardinalDestinations, palaceDestinations)
                .toList();
    }

    @Override
    public int getScore() {
        return 13;
    }

    @Override
    public boolean canBelongToWing() {
        return false;
    }

    @Override
    public boolean isRoyalPiece() {
        return false;
    }

    @Override
    protected boolean isScreenable() {
        return true;
    }

    private Stream<Intersection> findCardinalDestinations(
            Intersection from,
            AlivePieces alivePieces
    ) {
        return findValidRoute(from, Vector.cardinals(), alivePieces)
                .map(Route::getDestination);
    }

    private Stream<Intersection> findPalaceDestinations(
            Intersection from,
            AlivePieces alivePieces
    ) {
        return findValidRoute(from, from.getPalaceDiagonalVectors(), alivePieces)
                .filter(Route::containsOnlyPalace)
                .map(Route::getDestination);
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

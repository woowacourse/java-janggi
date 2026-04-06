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
    public boolean canMove(
            Intersection from,
            Intersection to,
            AlivePieces alivePieces
    ) {
        return movableIntersections(from, alivePieces)
                .contains(to);
    }

    @Override
    public List<Intersection> movableIntersections(
            Intersection from,
            AlivePieces alivePieces
    ) {
        Stream<Intersection> cardinalDestinations = Vector.cardinals()
                .stream()
                .flatMap(vector -> findReachableDestinations(from, vector, alivePieces));
        Stream<Intersection> palaceDestinations = from.getPalaceDiagonalVectors()
                .stream()
                .flatMap(vector -> findReachablePalaceDestinations(from, vector, alivePieces));

        return Stream.concat(cardinalDestinations, palaceDestinations)
                .toList();
    }

    @Override
    public boolean canBelongToWing() {
        return false;
    }

    @Override
    protected boolean isScreenable() {
        return true;
    }

    private Stream<Intersection> findReachableDestinations(
            Intersection from,
            Vector vector,
            AlivePieces alivePieces
    ) {
        return findValidRoute(from, vector, alivePieces)
                .map(Route::getDestination);
    }

    private Stream<Intersection> findReachablePalaceDestinations(
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

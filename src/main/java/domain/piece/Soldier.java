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

public final class Soldier extends StaticPositionedPiece {

    private static final int FAR_FROM_BASE_ROW = 3;
    private static final List<Integer> INITIAL_FILES = List.of(1, 3, 5, 7, 9);
    private static final MoveAmount MAX_MOVE_DISTANCE = new MoveAmount(1);

    private final MoveStrategy moveStrategy = new Straight(MAX_MOVE_DISTANCE);

    public Soldier(Side side) {
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
        Stream<Intersection> cardinalDestinations = findReachableDestinations(
                moveStrategy.getRoutes(from, getMovableCardinalVectors()),
                alivePieces
        );
        Stream<Intersection> palaceDestinations = findReachableDestinations(
                moveStrategy.getPalaceRoutes(from, side.toForwardDiagonals()),
                alivePieces
        );

        return Stream.concat(cardinalDestinations, palaceDestinations)
                .toList();
    }

    @Override
    public int getScore() {
        return 2;
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

    private List<Vector> getMovableCardinalVectors() {
        return List.of(
                side.toForward(),
                Vector.left(),
                Vector.right()
        );
    }

    private Stream<Intersection> findReachableDestinations(
            Collection<Route> routes,
            AlivePieces alivePieces
    ) {
        return routes.stream()
                .filter(route -> route.canReachDestinationThroughPath(alivePieces, side))
                .map(Route::getDestination);
    }
}

package domain.piece;

import domain.board.Intersection;
import domain.game.Side;
import domain.movement.MoveAmount;
import domain.movement.Route;
import domain.movement.Vector;
import domain.movement.strategy.MoveStrategy;
import domain.movement.strategy.Straight;
import java.util.Arrays;
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
        Vector forward = side.toForward();
        List<Route> forwardRoutes = moveStrategy.getRoutes(from, forward);
        Vector left = Vector.left();
        List<Route> leftRoutes = moveStrategy.getRoutes(from, left);
        Vector right = Vector.right();
        List<Route> rightRoutes = moveStrategy.getRoutes(from, right);

        List<Intersection> movableDestinations = concatRoutes(forwardRoutes, leftRoutes, rightRoutes)
                .filter(route -> route.isDestinationAvailable(alivePieces, side))
                .map(Route::getDestination)
                .toList();

        return List.copyOf(movableDestinations);
    }

    @Override
    public boolean canBelongToWing() {
        return false;
    }

    @SafeVarargs
    private Stream<Route> concatRoutes(List<Route>... routesCollection) {
        return Arrays.stream(routesCollection)
                .flatMap(Collection::stream);
    }
}

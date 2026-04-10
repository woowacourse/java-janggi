package domain.piece;

import domain.board.Intersection;
import domain.board.Palace;
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
    private final Palace palace = Palace.getInstance();

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
        Stream<Route> cardinalRoutes = findValidRoutes(
                moveStrategy.getRoutes(from, Vector.cardinals()),
                alivePieces
        );
        Stream<Route> palaceRoutes = findValidRoutes(
                moveStrategy.getRoutes(from, palace.getDiagonalVectors(from)),
                alivePieces
        ).filter(Route::containsOnlyPalace);

        return Stream.concat(cardinalRoutes, palaceRoutes)
                .map(Route::getDestination)
                .toList();
    }

    @Override
    public double getScore() {
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

    private Stream<Route> findValidRoutes(
            Collection<Route> routes,
            AlivePieces alivePieces
    ) {
        return routes.stream()
                .filter(route -> route.canReachDestinationThroughPath(alivePieces, side));
    }
}

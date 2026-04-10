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

public final class Soldier extends StaticPositionedPiece {

    private static final int FAR_FROM_BASE_ROW = 3;
    private static final List<Integer> INITIAL_FILES = List.of(1, 3, 5, 7, 9);
    private static final MoveAmount MAX_MOVE_DISTANCE = new MoveAmount(1);

    private final MoveStrategy moveStrategy = new Straight(MAX_MOVE_DISTANCE);
    private final Palace palace = Palace.getInstance();

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
        Stream<Route> cardinalRoutes = findValidRoutes(
                moveStrategy.getRoutes(from, getMovableCardinalVectors()),
                alivePieces
        );
        Stream<Route> palaceRoutes = findValidRoutes(
                moveStrategy.getRoutes(from, getSoldierPalaceVectors(from)),
                alivePieces
        ).filter(Route::containsOnlyPalace);

        return Stream.concat(cardinalRoutes, palaceRoutes)
                .map(Route::getDestination)
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

    private List<Vector> getSoldierPalaceVectors(Intersection from) {
        return palace.getDiagonalVectors(from).stream()
                .filter(vector -> side.toForwardDiagonals().contains(vector))
                .toList();
    }

    private Stream<Route> findValidRoutes(
            Collection<Route> routes,
            AlivePieces alivePieces
    ) {
        return routes.stream()
                .filter(route -> route.canReachDestinationThroughPath(alivePieces, side));
    }
}

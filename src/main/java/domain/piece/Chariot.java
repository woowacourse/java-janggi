package domain.piece;

import domain.board.Intersection;
import domain.game.Side;
import domain.movement.MoveAmount;
import domain.movement.Route;
import domain.movement.Vector;
import domain.movement.strategy.MoveStrategy;
import domain.movement.strategy.StraightMovement;
import java.util.ArrayList;
import java.util.List;

public final class Chariot extends StaticPositionedPiece {

    private static final int FAR_FROM_BASE_ROW = 0;
    private static final List<Integer> INITIAL_FILES = List.of(1, 9);
    private static final MoveAmount FORWARDABLE_AMOUNT = MoveAmount.maximum();

    private final MoveStrategy moveStrategy = new StraightMovement(FORWARDABLE_AMOUNT);

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
        List<Intersection> movableIntersections = new ArrayList<>();

        for (Vector vector : side.getAllDirections()) {
            List<Intersection> movableDestinations = findReachableDestinations(from, vector, alivePieces);
            movableIntersections.addAll(movableDestinations);
        }

        return List.copyOf(movableIntersections);
    }

    @Override
    public boolean canBelongToWing() {
        return false;
    }

    private List<Intersection> findReachableDestinations(
            Intersection from,
            Vector vector,
            AlivePieces alivePieces
    ) {
        return moveStrategy.getRoutes(from, vector)
                .stream()
                .filter(route -> route.canReachDestinationThroughPath(alivePieces, side))
                .map(Route::getDestination)
                .toList();
    }
}

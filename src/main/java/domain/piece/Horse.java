package domain.piece;

import domain.board.Intersection;
import domain.game.Side;
import domain.movement.MoveAmount;
import domain.movement.Route;
import domain.movement.Vector;
import domain.movement.strategy.ForwardAndDiagonal;
import domain.movement.strategy.MoveStrategy;
import java.util.ArrayList;
import java.util.List;

public final class Horse extends Piece {

    private static final MoveAmount DIAGONAL_MOVE_AMOUNT = new MoveAmount(1);

    private final MoveStrategy moveStrategy = new ForwardAndDiagonal(DIAGONAL_MOVE_AMOUNT);

    public Horse(Side side) {
        super(side);
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
            List<Intersection> reachableDestinations = findReachableDestinations(from, vector, alivePieces);
            movableIntersections.addAll(reachableDestinations);
        }

        return List.copyOf(movableIntersections);
    }

    @Override
    public boolean canBelongToWing() {
        return true;
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

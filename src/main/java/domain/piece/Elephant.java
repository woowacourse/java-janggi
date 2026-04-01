package domain.piece;

import domain.board.Intersection;
import domain.movement.Route;
import domain.movement.Vector;
import domain.movement.MoveAmount;
import domain.game.Side;
import domain.movement.strategy.ForwardAndDiagonalMovement;
import java.util.ArrayList;
import java.util.List;

public final class Elephant extends Piece {

    private static final MoveAmount DIAGONAL_MOVE_AMOUNT = new MoveAmount(2);

    private final ForwardAndDiagonalMovement movementStrategy = new ForwardAndDiagonalMovement();

    public Elephant(Side side) {
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
        return movementStrategy.getRoutes(from, DIAGONAL_MOVE_AMOUNT, vector)
                .stream()
                .filter(route -> route.canReachDestinationThroughPath(alivePieces, side))
                .map(Route::getDestination)
                .toList();
    }
}

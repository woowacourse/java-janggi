package domain.piece;

import domain.board.Intersection;
import domain.movement.Route;
import domain.movement.Vector;
import domain.movement.MoveAmount;
import domain.game.Side;
import domain.movement.strategy.StraightMovement;
import java.util.ArrayList;
import java.util.List;

public abstract class PalacePiece extends StaticPositionedPiece {

    private static final MoveAmount FORWARD_AMOUNT = new MoveAmount(1);

    private final StraightMovement movementStrategy = new StraightMovement(FORWARD_AMOUNT);

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
        List<Intersection> movableIntersections = new ArrayList<>();

        for (Vector vector : side.getAllDirections()) {
            List<Intersection> reachableDestinations = findReachableDestinations(from, vector, alivePieces);
            movableIntersections.addAll(reachableDestinations);
        }

        return List.copyOf(movableIntersections);
    }

    private List<Intersection> findReachableDestinations(
            Intersection from,
            Vector vector,
            AlivePieces alivePieces
    ) {
        return movementStrategy.getRoutes(from, vector)
                .stream()
                .filter(route -> route.canReachDestinationThroughPath(alivePieces, side))
                .map(Route::getDestination)
                .toList();
    }
}

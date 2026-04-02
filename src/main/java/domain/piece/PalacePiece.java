package domain.piece;

import domain.board.Intersection;
import domain.movement.Route;
import domain.movement.Vector;
import domain.movement.MoveAmount;
import domain.game.Side;
import domain.movement.strategy.MoveStrategy;
import domain.movement.strategy.Straight;
import java.util.ArrayList;
import java.util.List;

public abstract class PalacePiece extends StaticPositionedPiece {

    private static final MoveAmount MAX_MOVE_DISTANCE = new MoveAmount(1);

    private final MoveStrategy moveStrategy = new Straight(MAX_MOVE_DISTANCE);

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

        for (Vector vector : Vector.cardinals()) {
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
        return moveStrategy.getRoutes(from, vector)
                .stream()
                .filter(route -> route.canReachDestinationThroughPath(alivePieces, side))
                .map(Route::getDestination)
                .toList();
    }
}

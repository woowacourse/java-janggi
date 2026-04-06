package domain.piece;

import domain.board.Intersection;
import domain.game.Side;
import domain.movement.MoveAmount;
import domain.movement.Route;
import domain.movement.Vector;
import domain.movement.strategy.MoveStrategy;
import domain.movement.strategy.Straight;
import java.util.ArrayList;
import java.util.List;

public final class Cannon extends StaticPositionedPiece {

    private static final int FAR_FROM_BASE_ROW = 2;
    private static final int REQUIRED_SCREEN_COUNT = 1;
    private static final List<Integer> INITIAL_FILES = List.of(2, 8);
    private static final MoveAmount MAX_MOVE_DISTANCE = MoveAmount.maximum();

    private final MoveStrategy moveStrategy = new Straight(MAX_MOVE_DISTANCE);

    public Cannon(Side side) {
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

        for (Vector vector : Vector.cardinals()) {
            List<Intersection> reachableDestinations = findReachableDestinations(from, vector, alivePieces);
            movableIntersections.addAll(reachableDestinations);
        }

        return List.copyOf(movableIntersections);
    }

    @Override
    public boolean canBelongToWing() {
        return false;
    }

    @Override
    protected boolean isScreenable() {
        return false;
    }

    private List<Intersection> findReachableDestinations(
            Intersection from,
            Vector vector,
            AlivePieces alivePieces
    ) {
        return moveStrategy.getRoutes(from, vector)
                .stream()
                .filter(route -> isAvailableCannonRoute(route, alivePieces))
                .filter(route -> isDestinationAvailable(route, alivePieces))
                .map(Route::getDestination)
                .toList();
    }

    private boolean isAvailableCannonRoute(Route route, AlivePieces alivePieces) {
        List<Piece> pieces = route.getPiecesOnPath(alivePieces);

        if (pieces.size() != REQUIRED_SCREEN_COUNT) {
            return false;
        }

        return pieces.stream()
                .allMatch(Piece::isScreenable);
    }

    private boolean isDestinationAvailable(Route route, AlivePieces alivePieces) {
        Intersection destination = route.getDestination();
        if (alivePieces.isEmpty(destination)) {
            return true;
        }

        Piece destinationPiece = alivePieces.placedAt(destination);

        return route.isDestinationAvailable(alivePieces, side)
                && destinationPiece.isScreenable();
    }
}

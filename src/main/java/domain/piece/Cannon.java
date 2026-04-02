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

public final class Cannon extends StaticPositionedPiece {

    private static final int FAR_FROM_BASE_ROW = 2;
    private static final List<Integer> INITIAL_FILES = List.of(2, 8);
    private static final MoveAmount FORWARDABLE_AMOUNT = MoveAmount.maximum();

    private final MoveStrategy moveStrategy = new StraightMovement(FORWARDABLE_AMOUNT);

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

        for (Vector vector : side.getAllDirections()) {
            List<Intersection> reachableDestinations = findReachableDestinations(from, vector, alivePieces);
            movableIntersections.addAll(reachableDestinations);
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
                .filter(route -> isAvailableCannonRoute(route, alivePieces))
                .filter(route -> isDestinationAvailable(route, alivePieces))
                .map(Route::getDestination)
                .toList();
    }

    private boolean isAvailableCannonRoute(Route route, AlivePieces alivePieces) {
        List<Intersection> path = route.getPath();

        List<Intersection> notEmptyNodes = path.stream()
                .filter(alivePieces::isNotEmpty)
                .toList();

        return notEmptyNodes.size() == 1
                && isScreen(notEmptyNodes.getFirst(), alivePieces);
    }

    private boolean isDestinationAvailable(Route route, AlivePieces alivePieces) {
        Intersection destination = route.getDestination();
        Piece destinationPiece = alivePieces.placedAt(destination);

        return route.isDestinationAvailable(alivePieces, side)
                && isNotCannon(destinationPiece);
    }

    private boolean isScreen(
            Intersection intersection,
            AlivePieces alivePieces
    ) {
        Piece piece = alivePieces.placedAt(intersection);

        return intersection.isInBoard()
                && isNotCannon(piece);
    }

    private boolean isNotCannon(Piece piece) {
        return !(piece instanceof Cannon);
    }
}

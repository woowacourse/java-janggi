package domain.piece;

import domain.board.Intersection;
import domain.direction.Direction;
import domain.direction.MoveAmount;
import domain.game.Side;
import java.util.ArrayList;
import java.util.List;

public class Cannon extends StaticPositionedPiece {

    private static final MoveAmount FAR_FROM_BASE_ROW = new MoveAmount(2);
    private static final List<Integer> INITAL_FILES = List.of(2, 8);
    private static final MoveAmount MOVE_UNIT = new MoveAmount(1);

    public Cannon(Side side) {
        super(side);
    }

    @Override
    public List<Intersection> initAt() {
        Direction forwardDirection = side.getForwardDirection();

        return INITAL_FILES.stream()
                .map(this::currentIntersection)
                .map(intersection -> forwardDirection.moveForward(intersection, FAR_FROM_BASE_ROW))
                .toList();
    }

    private Intersection currentIntersection(int file) {
        return new Intersection(side.getBaseRow(), file);
    }

    public boolean canMove(
            Intersection from,
            Intersection to,
            AlivePieces alivePieces
    ) {
        return movableIntersections(from, alivePieces)
                .contains(to);
    }

    public List<Intersection> movableIntersections(
            Intersection from,
            AlivePieces alivePieces
    ) {
        List<Intersection> movableIntersections = new ArrayList<>();

        for (Direction direction : side.getAllDirections()) {
            addIfMovabale(from, direction, alivePieces, movableIntersections);
        }

        return List.copyOf(movableIntersections);
    }

    private void addIfMovabale(
            Intersection from,
            Direction direction,
            AlivePieces alivePieces,
            List<Intersection> movableIntersections
    ) {
        Intersection firstMet = firstMet(from, direction, alivePieces);
        if (isNotScreen(firstMet, alivePieces)) {
            return;
        }

        movableIntersections.addAll(movableIntersectionsBeyondScreen(firstMet, direction, alivePieces));
    }

    private Intersection firstMet(
            Intersection from,
            Direction direction,
            AlivePieces alivePieces
    ) {
        Intersection currentIntersection = direction.moveForward(from, MOVE_UNIT);
        while (currentIntersection.isInBoard() && alivePieces.isEmpty(currentIntersection)) {
            currentIntersection = direction.moveForward(currentIntersection, MOVE_UNIT);
        }

        return currentIntersection;
    }

    private boolean isNotScreen(
            Intersection intersection,
            AlivePieces alivePieces
    ) {
        return intersection.isOutOfBoard()
                || isCannon(alivePieces.placedAt(intersection));
    }

    private List<Intersection> movableIntersectionsBeyondScreen(
            Intersection screen,
            Direction direction,
            AlivePieces alivePieces
    ) {
        List<Intersection> movablaIntersections = new ArrayList<>();

        Intersection currentIntersection = direction.moveForward(screen, MOVE_UNIT);
        while (currentIntersection.isInBoard() && alivePieces.isEmpty(currentIntersection)) {
            movablaIntersections.add(currentIntersection);
            currentIntersection = direction.moveForward(currentIntersection, MOVE_UNIT);
        }

        if (isNotCannon(alivePieces.placedAt(currentIntersection))
                && alivePieces.placedOppositeSide(currentIntersection, side)) {
            movablaIntersections.add(currentIntersection);
        }

        return movablaIntersections;
    }

    private boolean isCannon(Piece piece) {
        return piece instanceof Cannon;
    }

    private boolean isNotCannon(Piece piece) {
        return !isCannon(piece);
    }
}

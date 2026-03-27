package domain.piece;

import domain.board.Intersection;
import domain.direction.Direction;
import domain.direction.MoveAmount;
import domain.game.Side;
import java.util.ArrayList;
import java.util.List;

public final class Chariot extends StaticPositionedPiece {

    private static final MoveAmount FAR_FROM_BASE_ROW = new MoveAmount(0);
    private static final List<Integer> INITIAL_FILES = List.of(1, 9);
    private static final MoveAmount MOVE_UNIT = new MoveAmount(1);

    public Chariot(Side side) {
        super(side);
    }

    @Override
    public List<Intersection> initAt() {
        Direction forwardDirection = side.getForwardDirection();

        return INITIAL_FILES.stream()
                .map(this::currentIntersection)
                .map(intersection -> forwardDirection.moveForward(intersection, FAR_FROM_BASE_ROW))
                .toList();
    }

    private Intersection currentIntersection(int file) {
        return new Intersection(side.getBaseRow(), file);
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

        for (Direction direction : side.getAllDirections()) {
            addReachableIntersections(from, direction, alivePieces, movableIntersections);
        }

        return List.copyOf(movableIntersections);
    }

    private void addReachableIntersections(
            Intersection from,
            Direction direction,
            AlivePieces alivePieces,
            List<Intersection> reachableIntersections
    ) {
        Intersection currentIntersection = direction.moveForward(from, MOVE_UNIT);
        while (isPassableIntersection(currentIntersection, alivePieces)) {
            reachableIntersections.add(currentIntersection);

            currentIntersection = direction.moveForward(currentIntersection, MOVE_UNIT);
        }

        if (alivePieces.placedOppositeSide(currentIntersection, side)) {
            reachableIntersections.add(currentIntersection);
        }
    }

    private boolean isPassableIntersection(Intersection intersection, AlivePieces alivePieces) {
        return intersection.isInBoard() && alivePieces.isEmpty(intersection);
    }
}

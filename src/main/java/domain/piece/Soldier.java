package domain.piece;

import domain.board.Intersection;
import domain.direction.Direction;
import domain.direction.MoveAmount;
import domain.game.Side;
import java.util.ArrayList;
import java.util.List;

public final class Soldier extends StaticPositionedPiece {

    private static final MoveAmount FAR_FROM_BASE_ROW = new MoveAmount(3);
    private static final List<Integer> INITIAL_FILES = List.of(1, 3, 5, 7, 9);
    private static final MoveAmount MOVE_AMOUNT = new MoveAmount(1);

    public Soldier(Side side) {
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

        Intersection forwardIntersection = side.getForwardDirection()
                .moveForward(from, MOVE_AMOUNT);
        addIfMovable(forwardIntersection, alivePieces, movableIntersections);

        Intersection leftIntersection = side.getLeftDirection()
                .moveForward(from, MOVE_AMOUNT);
        addIfMovable(leftIntersection, alivePieces, movableIntersections);

        Intersection rightIntersection = side.getRightDirection()
                .moveForward(from, MOVE_AMOUNT);
        addIfMovable(rightIntersection, alivePieces, movableIntersections);

        return List.copyOf(movableIntersections);
    }

    private void addIfMovable(
            Intersection destination,
            AlivePieces alivePieces,
            List<Intersection> movableIntersections
    ) {
        Piece rightPiece = alivePieces.placedAt(destination);

        if (destination.isOutOfBounds()) {
            return;
        }

        if (alivePieces.isEmpty(destination) || rightPiece.hasDifferentSide(side)) {
            movableIntersections.add(destination);
        }
    }
}

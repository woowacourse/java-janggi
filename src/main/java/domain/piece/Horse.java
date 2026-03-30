package domain.piece;

import domain.board.Intersection;
import domain.direction.Direction;
import domain.direction.MoveAmount;
import domain.game.Side;
import java.util.ArrayList;
import java.util.List;

public final class Horse extends Piece {

    private static final MoveAmount MOVE_UNIT = new MoveAmount(1);

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
        for (Direction direction : side.getAllDirections()) {
            addIfMovable(from, direction, alivePieces, movableIntersections);
        }

        return List.copyOf(movableIntersections);
    }

    @Override
    public boolean canBelongToWing() {
        return true;
    }

    private void addIfMovable(
            Intersection from,
            Direction direction,
            AlivePieces alivePieces,
            List<Intersection> movableIntersections
    ) {
        Intersection forwardIntersection = direction.moveForward(from, MOVE_UNIT);
        if (forwardIntersection.isOutOfBoard() || alivePieces.isNotEmpty(forwardIntersection)) {
            return;
        }

        Intersection leftDestination = direction.moveForwardLeft(forwardIntersection, MOVE_UNIT);
        if (leftDestination.isInBoard() && alivePieces.placedNotSameSide(leftDestination, side)) {
            movableIntersections.add(leftDestination);
        }

        Intersection rightDestination = direction.moveForwardRight(forwardIntersection, MOVE_UNIT);
        if (rightDestination.isInBoard() && alivePieces.placedNotSameSide(rightDestination, side)) {
            movableIntersections.add(rightDestination);
        }
    }
}

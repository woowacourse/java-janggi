package domain.piece;

import domain.board.Intersection;
import domain.direction.Direction;
import domain.direction.MoveAmount;
import domain.game.Side;
import java.util.ArrayList;
import java.util.List;

public abstract class PalacePiece extends StaticPositionedPiece {

    private static final MoveAmount MOVE_AMOUNT = new MoveAmount(1);

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

        for (Direction direction : side.getAllDirections()) {
            addIfMovable(
                    direction.moveForward(from, MOVE_AMOUNT),
                    alivePieces,
                    movableIntersections
            );
        }

        return List.copyOf(movableIntersections);
    }

    private void addIfMovable(
            Intersection destination,
            AlivePieces alivePieces,
            List<Intersection> movableIntersections
    ) {
        Piece rightPiece = alivePieces.placedAt(destination);

        if (destination.isOutOfBoard()) {
            return;
        }

        if (alivePieces.isEmpty(destination) || rightPiece.hasDifferentSide(side)) {
            movableIntersections.add(destination);
        }
    }
}

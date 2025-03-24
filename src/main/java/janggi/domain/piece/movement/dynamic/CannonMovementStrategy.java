package janggi.domain.piece.movement.dynamic;

import janggi.domain.piece.PieceType;
import janggi.domain.piece.Pieces;
import janggi.domain.piece.Position;
import janggi.domain.piece.Side;
import java.util.stream.IntStream;

public class CannonMovementStrategy implements DynamicMovementStrategy {

    @Override
    public boolean isLegalDestination(Side side, Position origin, Position destination) {
        if (origin.hasSameX(destination)) {
            return !origin.hasSameY(destination);
        }
        return origin.hasSameY(destination);
    }

    @Override
    public boolean isLegalPath(Pieces existingPieces, Side side, Position origin, Position destination) {
        Pieces piecesOnPath = getPiecesOnPath(existingPieces, origin, destination);
        if (piecesOnPath.size() != 1) {
            return false;
        }
        return !piecesOnPath.containsPieceType(PieceType.CANNON);
    }

    private Pieces getPiecesOnPath(Pieces existingPieces, Position origin, Position destination) {
        if (origin.hasSameX(destination)) {
            return getPiecesOnVerticalPath(existingPieces, origin, destination);
        }
        return getPiecesOnHorizontalPath(existingPieces, origin, destination);
    }

    private Pieces getPiecesOnHorizontalPath(Pieces existingPieces, Position origin, Position destination) {
        int startX = Math.min(origin.getX(), destination.getX()) + 1;
        int endX = Math.max(origin.getX(), destination.getX());

        return Pieces.from(
            IntStream.range(startX, endX)
                .mapToObj(x -> new Position(x, origin.getY()))
                .flatMap(position -> existingPieces.findByPosition(position).stream())
                .toList()
        );
    }

    private Pieces getPiecesOnVerticalPath(Pieces existingPieces, Position origin, Position destination) {
        int startY = Math.min(origin.getY(), destination.getY()) + 1;
        int endY = Math.max(origin.getY(), destination.getY());

        return Pieces.from(
            IntStream.range(startY, endY)
                .mapToObj(y -> new Position(origin.getX(), y))
                .flatMap(position -> existingPieces.findByPosition(position).stream())
                .toList()
        );
    }
}

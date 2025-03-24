package janggi.domain.piece.movement.dynamic;

import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Pieces;
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
        if (hasNoObstacle(existingPieces, origin, destination)) {
            return existingPieces.getValues().values().stream()
                .filter(piece -> piece.isSamePosition(destination))
                .map(piece -> piece.getSide() != side && !piece.getPieceType().equals(PieceType.CANNON))
                .findAny()
                .orElse(true);
        }
        return false;
    }

    private Piece findByPosition(Pieces existingPieces, Position position) {
        return existingPieces.getValues().values().stream()
            .filter(existingPiece -> existingPiece.isSamePosition(position))
            .findAny()
            .orElse(null);
    }

    private boolean hasNoObstacle(Pieces existingPieces, Position origin, Position destination) {
        Pieces piecesOnPath = getPiecesOnPath(existingPieces, origin, destination);
        if (piecesOnPath.size() != 1) {
            return false;
        }
        return piecesOnPath.getValues().values().stream()
            .noneMatch(piece -> piece.getPieceType().equals(PieceType.CANNON));
    }

    private Pieces getPiecesOnPath(Pieces existingPieces, Position origin, Position destination) {
        if (origin.hasSameX(destination)) {
            return getPiecesOnVerticalPath(existingPieces, origin, destination);
        }
        return getPiecesOnHorizontalPath(existingPieces, origin, destination);
    }

    private Pieces getPiecesOnHorizontalPath(Pieces existingPieces, Position origin, Position destination) {
        int originX = origin.getX();
        int originY = origin.getY();

        return Pieces.from(
            IntStream.range(Math.min(originX, destination.getX()) + 1, Math.max(originX, destination.getX()))
                .filter(x -> existingPieces.hasPieceOnPosition(new Position(x, originY)))
                .mapToObj(x -> findByPosition(existingPieces, new Position(x, originY)))
                .toList());
    }

    private Pieces getPiecesOnVerticalPath(Pieces existingPieces, Position origin, Position destination) {
        int originX = origin.getX();
        int originY = origin.getY();

        return Pieces.from(
            IntStream.range(Math.min(originY, destination.getY()) + 1, Math.max(originY, destination.getY()))
                .filter(y -> existingPieces.hasPieceOnPosition(new Position(originX, y)))
                .mapToObj(y -> findByPosition(existingPieces, new Position(originX, y)))
                .toList());
    }
}

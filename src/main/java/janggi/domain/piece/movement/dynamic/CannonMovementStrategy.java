package janggi.domain.piece.movement.dynamic;

import janggi.domain.piece.PieceType;
import janggi.domain.piece.Position;
import janggi.domain.piece.Side;
import janggi.domain.piece.pieces.Pieces;
import janggi.domain.piece.pieces.PiecesView;
import java.util.stream.IntStream;

public class CannonMovementStrategy extends DynamicMovementStrategy {

    @Override
    public boolean isLegalDestination(Side side, Position origin, Position destination) {
        if (origin.hasSameX(destination)) {
            return !origin.hasSameY(destination);
        }
        return origin.hasSameY(destination);
    }

    @Override
    public boolean isLegalPath(PiecesView existingPieces, Side side, Position origin, Position destination) {
        PiecesView piecesOnPath = getPiecesOnPath(existingPieces, origin, destination);
        if (piecesOnPath.size() != 1) {
            return false;
        }
        return !piecesOnPath.containsPieceType(PieceType.CANNON);
    }

    private PiecesView getPiecesOnPath(PiecesView existingPieces, Position origin, Position destination) {
        if (origin.hasSameX(destination)) {
            return getPiecesOnVerticalPath(existingPieces, origin, destination);
        }
        return getPiecesOnHorizontalPath(existingPieces, origin, destination);
    }

    private PiecesView getPiecesOnHorizontalPath(PiecesView existingPieces, Position origin, Position destination) {
        int startX = Math.min(origin.x(), destination.x()) + 1;
        int endX = Math.max(origin.x(), destination.x());

        return Pieces.fromPieceViews(
            IntStream.range(startX, endX)
                .mapToObj(x -> new Position(x, origin.y()))
                .flatMap(position -> existingPieces.findByPosition(position).stream())
                .toList()
        );
    }

    private PiecesView getPiecesOnVerticalPath(PiecesView existingPieces, Position origin, Position destination) {
        int startY = Math.abs(origin.y() - destination.y()) + 1;
        int endY = Math.max(origin.y(), destination.y());

        return Pieces.fromPieceViews(
            IntStream.range(startY, endY)
                .mapToObj(y -> new Position(origin.x(), y))
                .flatMap(position -> existingPieces.findByPosition(position).stream())
                .toList()
        );
    }
}

package janggi.piece;

import janggi.board.Palace;
import janggi.board.point.Point;
import java.util.Set;

public abstract class PalaceAffectedPiece extends Piece {

    public PalaceAffectedPiece(Camp camp) {
        super(camp);
    }

    @Override
    public final void validateMove(Point fromPoint, Point toPoint, Set<Piece> piecesOnRoute) {
        if (isOutsidePalace(fromPoint)) {
            validateNonPalaceMove(fromPoint, toPoint);
        }
        if (!isOutsidePalace(fromPoint)) {
            validatePalaceMove(fromPoint, toPoint);
        }
        validateObstacleOnRoute(piecesOnRoute);
    }

    protected abstract void validateNonPalaceMove(Point fromPoint, Point toPoint);

    protected abstract void validatePalaceMove(Point fromPoint, Point toPoint);

    protected abstract void validateObstacleOnRoute(Set<Piece> piecesOnRoute);

    protected final boolean isOutsidePalace(Point point) {
        return !Palace.isInsidePalace(point);
    }

    protected final boolean isDiagonalPalaceMove(Point fromPoint, Point toPoint) {
        return Palace.isDiagonalPalaceMoveAllowed(fromPoint, toPoint)
                && fromPoint.isDiagonal(toPoint);
    }
}

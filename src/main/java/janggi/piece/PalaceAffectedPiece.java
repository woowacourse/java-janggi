package janggi.piece;

import janggi.board.Board;
import janggi.board.Palace;
import janggi.board.point.Point;

public abstract class PalaceAffectedPiece extends Piece {

    public PalaceAffectedPiece(Camp camp, Board board) {
        super(camp, board);
    }

    @Override
    public final void validateMove(Point fromPoint, Point toPoint) {
        if (isOutsidePalace(fromPoint)) {
            validateNonPalaceMove(fromPoint, toPoint);
            return;
        }
        validatePalaceMove(fromPoint, toPoint);
    }

    protected abstract void validatePalaceMove(Point fromPoint, Point toPoint);

    protected abstract void validateNonPalaceMove(Point fromPoint, Point toPoint);

    public final boolean isOutsidePalace(Point point) {
        return !Palace.isInsidePalace(point);
    }

    public final boolean isDiagonalPalaceMove(Point fromPoint, Point toPoint) {
        return Palace.isDiagonalPalaceMoveAllowed(fromPoint, toPoint)
                && fromPoint.isDiagonal(toPoint);
    }
}

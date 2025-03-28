package janggi.piece;

import janggi.board.point.Point;

public abstract class PalaceRestrictedPiece extends PalaceAffectedPiece {

    public PalaceRestrictedPiece(Camp camp) {
        super(camp);
    }

    @Override
    protected void validateNonPalaceMove(Point fromPoint, Point toPoint) {
        throw new IllegalArgumentException("궁 안에서만 이동할 수 있습니다.");
    }

    @Override
    protected void validatePalaceMove(Point fromPoint, Point toPoint) {
        if (isOutsidePalace(toPoint)) {
            throw new IllegalArgumentException("궁 안에서만 이동할 수 있습니다.");
        }
        validatePalaceRestrictedMove(fromPoint, toPoint);
    }

    protected abstract void validatePalaceRestrictedMove(Point fromPoint, Point toPoint);
}

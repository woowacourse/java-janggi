package janggi.piece;

import janggi.board.Board;
import janggi.board.point.Point;

public abstract class PalaceRestrictedPiece extends PalaceAffectedPiece {

    public PalaceRestrictedPiece(Camp camp, Board board) {
        super(camp, board);
    }

    @Override
    protected void validatePalaceMove(Point fromPoint, Point toPoint) {
        if (isOutsidePalace(toPoint)) {
            throw new IllegalArgumentException("궁 안에서만 이동할 수 있습니다.");
        }
        validatePalaceRestrictedMove(fromPoint, toPoint);
    }

    @Override
    protected void validateNonPalaceMove(Point fromPoint, Point toPoint) {
        throw new IllegalArgumentException("궁 안에서만 이동할 수 있습니다.");
    }

    protected abstract void validatePalaceRestrictedMove(Point fromPoint, Point toPoint);
}

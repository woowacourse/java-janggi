package janggi.piece;

import janggi.board.Board;
import janggi.board.point.Point;

public final class Guard extends PalaceRestrictedPiece {

    public Guard(Camp camp, Board board) {
        super(camp, board);
    }

    @Override
    public void validateMove(Point fromPoint, Point toPoint) {
        if (isBothOutsidePalace(fromPoint, toPoint)) {
            throw new IllegalArgumentException("사는 궁 안에서만 이동할 수 있습니다.");
        }
        if (!(isDiagonalPalaceMove(fromPoint, toPoint) && fromPoint.isOneDiagonalStepAway(toPoint))
                && !fromPoint.isOneStepAway(toPoint)) {
            throw new IllegalArgumentException("사는 직선 또는 대각선 한 칸만 이동할 수 있습니다.");
        }
    }

    @Override
    protected boolean canCapture(Piece otherPiece) {
        return getCamp() != otherPiece.getCamp();
    }

    @Override
    public PieceSymbol getPieceSymbol() {
        return PieceSymbol.GUARD;
    }

    @Override
    public int getPoint() {
        return PieceSymbol.GUARD.getPoint();
    }
}

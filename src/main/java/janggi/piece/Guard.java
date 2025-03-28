package janggi.piece;

import janggi.board.Board;
import janggi.board.point.Point;

public final class Guard extends PalaceRestrictedPiece {

    public Guard(Camp camp, Board board) {
        super(camp, board);
    }

    @Override
    protected void validatePalaceRestrictedMove(Point fromPoint, Point toPoint) {
        if (!(isDiagonalPalaceMove(fromPoint, toPoint) && fromPoint.isOneDiagonalStepAway(toPoint))
                && !fromPoint.isOneStepAway(toPoint)) {
            throw new IllegalArgumentException("사는 직선 또는 대각선 한 칸만 이동할 수 있습니다.");
        }
    }

    @Override
    protected boolean canCapture(Piece otherPiece) {
        return isEnemy(otherPiece);
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

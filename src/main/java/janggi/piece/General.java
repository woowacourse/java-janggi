package janggi.piece;

import janggi.board.Board;

import janggi.board.point.Point;

public final class General extends PalaceRestrictedPiece {

    public General(Camp camp, Board board) {
        super(camp, board);
    }

    @Override
    protected void validatePalaceRestrictedMove(Point fromPoint, Point toPoint) {
        if (!(isDiagonalPalaceMove(fromPoint, toPoint) && fromPoint.isOneDiagonalStepAway(toPoint))
                && !fromPoint.isOneStepAway(toPoint)) {
            throw new IllegalArgumentException("장군은 직선 또는 대각선 한 칸만 이동할 수 있습니다.");
        }
    }

    @Override
    protected boolean canCapture(Piece otherPiece) {
        return isEnemy(otherPiece);
    }

    @Override
    public PieceSymbol getPieceSymbol() {
        return PieceSymbol.GENERAL;
    }

    @Override
    public int getPoint() {
        return PieceSymbol.GENERAL.getPoint();
    }
}

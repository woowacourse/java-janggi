package janggi.piece;

import janggi.board.Board;

import janggi.board.point.Point;

public final class General extends PalaceRestrictedPiece {

    public General(Camp camp, Board board) {
        super(camp, board);
    }

    @Override
    public void validateMove(Point fromPoint, Point toPoint) {
        if (isBothOutsidePalace(fromPoint, toPoint)) {
            throw new IllegalArgumentException("장군은 궁 안에서만 이동할 수 있습니다.");
        }
        if (!(isDiagonalPalaceMove(fromPoint, toPoint) && fromPoint.isOneDiagonalStepAway(toPoint))
                && !fromPoint.isOneStepAway(toPoint)) {
            throw new IllegalArgumentException("장군은 직선 또는 대각선 한 칸만 이동할 수 있습니다.");
        }
    }

    @Override
    public PieceSymbol getPieceSymbol() {
        return PieceSymbol.GENERAL;
    }

    @Override
    protected boolean canCapture(Piece otherPiece) {
        return getCamp() != otherPiece.getCamp();
    }
}

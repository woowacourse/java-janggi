package janggi.piece;

import janggi.board.Board;
import janggi.board.point.Point;

public final class SoldierJol extends PalaceAffectedPiece {

    public SoldierJol(Board board) {
        super(Camp.CHU, board);
    }

    @Override
    protected void validatePalaceMove(Point fromPoint, Point toPoint) {
        if (fromPoint.isDiagonal(toPoint)) {
            validateDiagonalPalaceMove(fromPoint, toPoint);
            return;
        }
        validateJolMove(fromPoint, toPoint);
    }

    @Override
    protected void validateNonPalaceMove(Point fromPoint, Point toPoint) {
        validateJolMove(fromPoint, toPoint);
    }

    private void validateDiagonalPalaceMove(Point fromPoint, Point toPoint) {
        if (!isDiagonalPalaceMove(fromPoint, toPoint)) {
            throw new IllegalArgumentException("졸이 대각선으로 이동하려면, 허용된 지점에서만 가능합니다.");
        }
        if (!fromPoint.isOneDiagonalStepAway(toPoint) || toPoint.isBehind(fromPoint)) {
            throw new IllegalArgumentException("졸은 대각선으로 이동할 수 있는 경우 뒤로 갈 수 없으며, 한 칸만 이동할 수 있습니다.");
        }
    }

    private void validateJolMove(Point fromPoint, Point toPoint) {
        if (toPoint.isBehind(fromPoint) || !fromPoint.isOneStepAway(toPoint)) {
            throw new IllegalArgumentException("졸은 뒤로 갈 수 없으며, 앞 또는 양 옆으로 한 칸만 움직일 수 있습니다.");
        }
    }

    @Override
    protected boolean canCapture(Piece otherPiece) {
        return isEnemy(otherPiece);
    }

    @Override
    public PieceSymbol getPieceSymbol() {
        return PieceSymbol.SOLDIER_JOL;
    }

    @Override
    public int getPoint() {
        return PieceSymbol.SOLDIER_JOL.getPoint();
    }
}

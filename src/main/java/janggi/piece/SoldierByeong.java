package janggi.piece;

import janggi.board.Board;
import janggi.board.point.Point;

public final class SoldierByeong extends PalaceAffectedPiece {

    public SoldierByeong(Board board) {
        super(Camp.HAN, board);
    }

    @Override
    public void validateMove(Point fromPoint, Point toPoint) {
        if (isInsidePalace(fromPoint)) {
            validatePalaceMove(fromPoint, toPoint);
            return;
        }
        validateNonPalaceMove(fromPoint, toPoint);
    }

    private void validatePalaceMove(Point fromPoint, Point toPoint) {
        if (fromPoint.isDiagonal(toPoint)) {
            validateDiagonalPalaceMove(fromPoint, toPoint);
            return;
        }
        validateByeongMove(fromPoint, toPoint);
    }

    private void validateDiagonalPalaceMove(Point fromPoint, Point toPoint) {
        if (!isDiagonalPalaceMove(fromPoint, toPoint)) {
            throw new IllegalArgumentException("병이 대각선으로 이동하려면, 허용된 지점에서만 가능합니다.");
        }
        if (!fromPoint.isOneDiagonalStepAway(toPoint) || fromPoint.isBehind(toPoint)) {
            throw new IllegalArgumentException("병은 대각선으로 이동할 수 있는 경우 뒤로 갈 수 없으며, 한 칸만 이동할 수 있습니다.");
        }
    }

    private void validateNonPalaceMove(Point fromPoint, Point toPoint) {
        validateByeongMove(fromPoint, toPoint);
    }

    private void validateByeongMove(Point fromPoint, Point toPoint) {
        if (fromPoint.isBehind(toPoint) || !fromPoint.isOneStepAway(toPoint)) {
            throw new IllegalArgumentException("병은 뒤로 갈 수 없으며, 앞 또는 양 옆으로 한 칸만 움직일 수 있습니다.");
        }
    }

    @Override
    protected boolean canCapture(Piece otherPiece) {
        return isEnemy(otherPiece);
    }

    @Override
    public PieceSymbol getPieceSymbol() {
        return PieceSymbol.SOLDIER_BYEONG;
    }

    @Override
    public int getPoint() {
        return PieceSymbol.SOLDIER_BYEONG.getPoint();
    }
}

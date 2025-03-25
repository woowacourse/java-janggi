package janggi.piece;

import janggi.board.Board;
import janggi.board.point.Point;

public final class SoldierByeong extends Piece {

    public SoldierByeong(Board board) {
        super(Camp.HAN, board);
    }

    @Override
    public void validateMove(Point fromPoint, Point toPoint) {
        if (fromPoint.isBehind(toPoint) || !fromPoint.isOneStepAway(toPoint)) {
            throw new IllegalArgumentException("병은 뒤로 갈 수 없으며, 앞 또는 양 옆으로 한 칸만 움직일 수 있습니다.");
        }
    }

    @Override
    public PieceSymbol getPieceSymbol() {
        return PieceSymbol.SOLDIER_BYEONG;
    }

    @Override
    protected boolean canCapture(Piece otherPiece) {
        return getCamp() != otherPiece.getCamp();
    }
}

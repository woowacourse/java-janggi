package janggi.piece;

import janggi.board.Board;
import janggi.board.point.Point;

public final class SoldierJol extends Piece {

    public SoldierJol(Board board) {
        super(Camp.CHU, board);
    }

    @Override
    public void validateMove(Point fromPoint, Point toPoint) {
        if (toPoint.isBehind(fromPoint) || !fromPoint.isOneStepAway(toPoint)) {
            throw new IllegalArgumentException("졸은 뒤로 갈 수 없으며, 앞 또는 양 옆으로 한 칸만 움직일 수 있습니다.");
        }
    }

    @Override
    public PieceSymbol getPieceSymbol() {
        return PieceSymbol.SOLDIER_JOL;
    }

    @Override
    protected boolean canCapture(Piece otherPiece) {
        return getCamp() != otherPiece.getCamp();
    }
}

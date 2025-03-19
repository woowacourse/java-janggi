package janggi.piece;

import janggi.Camp;
import janggi.PieceSymbol;
import janggi.Point;
import janggi.board.Board;

public final class Chariot extends Piece {

    public Chariot(Camp camp, Board board) {
        super(camp, board);
    }

    @Override
    public void validateMove(Point fromPoint, Point toPoint) {
        if (!(fromPoint.isHorizontal(toPoint) || fromPoint.isVertical(toPoint))) {
            throw new IllegalArgumentException("차는 상하좌우로 움직여야 합니다.");
        }
    }

    @Override
    public PieceSymbol getPieceSymbol() {
        return PieceSymbol.CHARIOT;
    }
}

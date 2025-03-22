package janggi.piece;

import janggi.board.Point;
import janggi.board.Board;
import janggi.camp.Camp;

public final class Guard extends Piece {

    public Guard(Camp camp, Board board) {
        super(camp, board);
    }

    @Override
    public void validateMove(Point fromPoint, Point toPoint) {
    }

    @Override
    public PieceSymbol getPieceSymbol() {
        return PieceSymbol.GUARD;
    }
}

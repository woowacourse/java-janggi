package janggi.piece;

import janggi.view.PieceSymbol;
import janggi.board.point.Point;
import janggi.board.Board;

public final class General extends Piece {

    public General(Camp camp, Board board) {
        super(camp, board);
    }

    @Override
    public void validateMove(Point fromPoint, Point toPoint) {
    }

    @Override
    public PieceSymbol getPieceSymbol() {
        return PieceSymbol.GENERAL;
    }
}

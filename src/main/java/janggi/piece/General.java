package janggi.piece;

import janggi.board.Board;
import janggi.board.Point;
import janggi.camp.Camp;
import janggi.view.PieceSymbol;

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

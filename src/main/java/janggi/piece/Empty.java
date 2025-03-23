package janggi.piece;

import janggi.Camp;
import janggi.PieceSymbol;
import janggi.Point;

public class Empty extends Piece {

    public Empty(Camp camp) {
        super(camp);
    }

    @Override
    public void validateMove(Point fromPoint, Point toPoint) {
    }

    @Override
    public PieceSymbol getPieceSymbol() {
        return PieceSymbol.EMPTY;
    }
}

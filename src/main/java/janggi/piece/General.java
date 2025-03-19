package janggi.piece;

import janggi.Camp;
import janggi.PieceSymbol;
import janggi.Point;

public final class General extends Piece {

    public General(Camp camp) {
        super(camp);
    }

    @Override
    public void validateMove(Point fromPoint, Point toPoint) {
    }

    @Override
    public PieceSymbol getPieceSymbol() {
        return PieceSymbol.GENERAL;
    }
}

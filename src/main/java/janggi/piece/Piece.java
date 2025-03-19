package janggi.piece;

import janggi.Camp;
import janggi.PieceSymbol;
import janggi.Point;

public abstract class Piece {

    private final Camp camp;

    public Piece(Camp camp) {
        this.camp = camp;
    }

    public abstract void validateMove(Point fromPoint, Point toPoint);

    public abstract PieceSymbol getPieceSymbol();

    protected boolean isBottom() {
        return camp.isBottom();
    }

    public Camp getCamp() {
        return camp;
    }
}

package janggi.domain.piece;

import janggi.domain.Side;
import janggi.domain.policy.RoutePolicy;

public abstract class BasePiece implements Piece {
    protected RoutePolicy routePolicy;
    protected Side side;

    public BasePiece(RoutePolicy routePolicy, Side side) {
        this.routePolicy = routePolicy;
        this.side = side;
    }

    @Override
    public boolean isPo() {
        return false;
    }
}

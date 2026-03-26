package janggi.domain.piece;

import janggi.domain.Side;
import janggi.domain.policy.RoutePolicy;

public abstract class ActivePiece extends BasePiece {
    protected RoutePolicy routePolicy;

    public ActivePiece(RoutePolicy routePolicy, Side side, PieceType pieceType) {
        super(side, pieceType);
        this.routePolicy = routePolicy;
    }
}

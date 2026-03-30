package janggi.domain.piece;

import janggi.domain.Side;
import janggi.domain.policy.RoutePolicy;

public abstract class ActivePiece extends BasePiece {
    protected static final String INVALID_DESTINATION_MESSAGE = "올바른 도착 지점이 아닙니다.";
    protected static final String UNMOVABLE_ROUTE_MESSAGE = "이동할 수 없는 경로입니다.";

    protected RoutePolicy routePolicy;

    public ActivePiece(RoutePolicy routePolicy, Side side, PieceType pieceType) {
        super(side, pieceType);
        this.routePolicy = routePolicy;
    }
}

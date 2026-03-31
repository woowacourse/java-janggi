package janggi.domain.piece;

import janggi.domain.Movements;
import janggi.domain.Position;
import janggi.domain.Route;
import janggi.domain.Side;
import janggi.domain.board.BaseBoard;
import janggi.domain.policy.RoutePolicy;
import java.util.List;
import java.util.Optional;

public abstract class StepPiece extends ActivePiece {
    private final List<Movements> moveRange;

    public StepPiece(List<Movements> moveRange, RoutePolicy routePolicy, Side side, PieceType pieceType) {
        super(routePolicy, side, pieceType);
        this.moveRange = moveRange;
    }

    @Override
    public Route findRoute(Position start, Position end) {
        return moveRange.stream()
                .map(movements -> movements.calculatePath(start))
                .flatMap(Optional::stream)
                .filter(route -> route.isArrivalPoint(end))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_DESTINATION_MESSAGE));
    }

    @Override
    public void validateRoute(Route route, BaseBoard boardInfo) {
        if (!routePolicy.isMovable(route, side, boardInfo)) {
            throw new IllegalArgumentException(UNMOVABLE_ROUTE_MESSAGE);
        }
    }
}

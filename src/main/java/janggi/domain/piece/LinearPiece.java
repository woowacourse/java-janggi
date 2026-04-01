package janggi.domain.piece;

import janggi.domain.Movement;
import janggi.domain.Position;
import janggi.domain.Route;
import janggi.domain.Side;
import janggi.domain.board.BaseBoard;
import janggi.domain.policy.RoutePolicy;
import java.util.stream.Stream;

public abstract class LinearPiece extends ActivePiece {
    public LinearPiece(RoutePolicy routePolicy, Side side, PieceType pieceType) {
        super(routePolicy, side, pieceType);
    }

    @Override
    public Route findRoute(Position start, Position end) {
        try {
            Movement direction = start.getLinearDirection(end);
            int distance = start.calculateLinearDistance(end);

            return calculatePath(start, direction, distance);
        } catch (IllegalStateException e) {
            throw new IllegalArgumentException(INVALID_DESTINATION_MESSAGE);
        }

    }

    protected Route calculatePath(Position start, Movement direction, int dist) {
        return new Route(Stream
                .iterate(start, current -> current.move(direction))
                .limit(Math.abs(dist) + 1)
                .toList());
    }
}

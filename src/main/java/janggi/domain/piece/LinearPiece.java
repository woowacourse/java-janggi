package janggi.domain.piece;

import janggi.domain.Movement;
import janggi.domain.Position;
import janggi.domain.Route;
import janggi.domain.Side;
import janggi.domain.board.Palace;
import janggi.domain.policy.RoutePolicy;
import java.util.stream.Stream;

public abstract class LinearPiece extends ActivePiece {
    public LinearPiece(RoutePolicy routePolicy, Side side, PieceType pieceType) {
        super(routePolicy, side, pieceType);
    }

    @Override
    public Route findRoute(Position start, Position end) {
        try {
            return getRoute(start, end);
        } catch (IllegalStateException e) {
            throw new IllegalArgumentException(INVALID_DESTINATION_MESSAGE);
        }
    }

    private Route getRoute(Position start, Position end) {
        Movement direction = start.calculateDirection(end);

        if(!isValidDirection(start, end, direction)) {
            throw new IllegalArgumentException(INVALID_DESTINATION_MESSAGE);
        }

        int distance = start.calculateLinearDistance(end);

        return calculatePath(start, direction, distance);
    }

    private boolean isValidDirection(Position start, Position end, Movement direction) {
        if (!direction.isDiagonal()) {
            return true;
        }
        return Palace.isDiagonalMove(start, end);
    }

    protected Route calculatePath(Position start, Movement direction, int dist) {
        return new Route(Stream
                .iterate(start, current -> current.move(direction))
                .limit(Math.abs(dist) + 1)
                .toList());
    }
}

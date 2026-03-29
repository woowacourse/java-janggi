package janggi.domain.piece;

import janggi.domain.board.BoardInterface;
import janggi.domain.Movement;
import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.policy.RoutePolicy;

import java.util.List;
import java.util.stream.Stream;

public abstract class LinearPiece extends ActivePiece {
    public LinearPiece(RoutePolicy routePolicy, Side side, PieceType pieceType) {
        super(routePolicy, side, pieceType);
    }

    @Override
    public List<Position> findRoute(Position start, Position end) {
        boolean isVertical = start.isVertical(end);
        boolean isHorizon = start.isHorizon(end);
        if (!isVertical && !isHorizon) {
            throw new IllegalArgumentException(INVALID_DESTINATION_MESSAGE);
        }

        int dist = start.calculateDistance(end, isVertical);
        return calculatePath(start, isVertical, dist);
    }

    @Override
    public void validateRoute(List<Position> path, BoardInterface boardInterface) {
        if (!routePolicy.isMovable(path, side, boardInterface)) {
            throw new IllegalArgumentException(UNMOVABLE_ROUTE_MESSAGE);
        }
    }

    private List<Position> calculatePath(Position start, boolean isVertical, int dist) {
        Movement movement = resolveMovement(isVertical, dist);
        return Stream
                .iterate(start, current -> current.move(movement))
                .limit(Math.abs(dist) + 1)
                .toList();
    }

    private Movement resolveMovement(boolean isVertical, int dist) {
        if (isVertical) {
            return resolveVerticalMovement(dist);
        }
        return resolveHorizontalMovement(dist);
    }

    private Movement resolveVerticalMovement(int dist) {
        if (dist < 0) {
            return Movement.UP;
        }
        return Movement.DOWN;
    }

    private Movement resolveHorizontalMovement(int dist) {
        if (dist < 0) {
            return Movement.LEFT;
        }
        return Movement.RIGHT;
    }
}

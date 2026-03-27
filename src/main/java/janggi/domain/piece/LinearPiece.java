package janggi.domain.piece;

import janggi.domain.board.BoardInterface;
import janggi.domain.Movement;
import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.policy.RoutePolicy;

import java.util.List;

public abstract class LinearPiece extends ActivePiece {
    public LinearPiece(RoutePolicy routePolicy, Side side, PieceType pieceType) {
        super(routePolicy, side, pieceType);
    }

    @Override
    public List<Position> findRoute(Position start, Position end) {
        boolean isVertical = start.isVertical(end);
        boolean isHorizon = start.isHorizon(end);
        if (!isVertical && !isHorizon) {
            throw new IllegalArgumentException("올바른 도착 지점이 아닙니다.");
        }

        int dist = start.calculateDistance(end, isVertical);
        return calculatePath(start, isVertical, dist);
    }

    @Override
    public void validateRoute(List<Position> path, BoardInterface boardInterface) {
        if(!routePolicy.isMovable(path, side, boardInterface)){
            throw new IllegalArgumentException("이동할 수 없는 경로입니다.");
        }
    }

    private List<Position> calculatePath(Position start, boolean isVertical, int dist) {
        Movement movement = resolveMovement(isVertical, dist);
        return java.util.stream.Stream
                .iterate(start, current -> current.move(movement))
                .limit(Math.abs(dist) + 1)
                .toList();
    }

    private Movement resolveMovement(boolean isVertical, int dist) {
        if (isVertical && dist < 0) {
            return Movement.UP;
        }
        if (isVertical) {
            return Movement.DOWN;
        }
        if (dist < 0) {
            return Movement.LEFT;
        }
        return Movement.RIGHT;
    }
}

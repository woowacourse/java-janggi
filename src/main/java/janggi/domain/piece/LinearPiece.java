package janggi.domain.piece;

import janggi.domain.board.BoardInterface;
import janggi.domain.Movement;
import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.policy.RoutePolicy;

import java.util.ArrayList;
import java.util.List;

public abstract class LinearPiece extends BasePiece {

    public LinearPiece(RoutePolicy routePolicy, Side side) {
        super(routePolicy, side);
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
    public boolean isMovable(List<Position> path, BoardInterface boardInterface) {
        return routePolicy.isMovable(path, side, boardInterface);
    }

    private List<Position> calculatePath(Position start, boolean isVertical, int dist) {
        Movement movement = resolveMovement(isVertical, dist);
        List<Position> calculatedPath = new ArrayList<>(List.of(start));

        for (int i = 0; i < dist; i++) {
            calculatedPath.add(calculatedPath.getLast().move(movement));
        }
        return calculatedPath;
    }

    private Movement resolveMovement(boolean isVertical, int dist) {
        if (isVertical && dist < 0) {
            return Movement.DOWN;
        }
        if (isVertical) {
            return Movement.UP;
        }
        if (dist < 0) {
            return Movement.LEFT;
        }
        return Movement.RIGHT;
    }
}

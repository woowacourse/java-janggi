package janggi.domain.piece;

import janggi.domain.BoardInterface;
import janggi.domain.Movement;
import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.policy.RoutePolicy;

import java.util.ArrayList;
import java.util.List;

public abstract class StepPatternPiece extends BasePiece {
    private final List<List<Movement>> moveRange;

    public StepPatternPiece(List<List<Movement>> moveRange, RoutePolicy routePolicy, Side side) {
        super(routePolicy, side);
        this.moveRange = moveRange;
    }

    @Override
    public List<Position> findRoute(Position start, Position end) {
        for (List<Movement> movements : moveRange) {
            List<Position> calculatedPath = calculatePath(start, movements);
            if (calculatedPath.getLast().equals(end)) {
                return calculatedPath;
            }
        }
        throw new IllegalArgumentException("올바른 도착 지점이 아닙니다.");
    }

    @Override
    public boolean isMovable(List<Position> path, BoardInterface boardInterface) {
        return routePolicy.isMovable(path, side, boardInterface);
    }

    private List<Position> calculatePath(Position start, List<Movement> path) {
        List<Position> calculatedPath = new ArrayList<>(List.of(start));
        for (Movement movement : path) {
            Position step = calculatedPath.getLast().move(movement);
            calculatedPath.add(step);
        }
        return calculatedPath;
    }
}

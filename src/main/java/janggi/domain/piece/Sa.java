package janggi.domain.piece;

import janggi.domain.BoardInterface;
import janggi.domain.Movement;
import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.strategy.DefaultMoveStrategy;

import java.util.ArrayList;
import java.util.List;

public class Sa extends Started {
    private final List<List<Movement>> MOVE_RANGE = List.of(
            List.of(Movement.UP),
            List.of(Movement.DOWN),
            List.of(Movement.LEFT),
            List.of(Movement.RIGHT)
    );


    public Sa(Side side) {
        super(new DefaultMoveStrategy(), side);
    }

    @Override
    public List<Position> findRoute(Position start, Position end) {
        for (List<Movement> movements : MOVE_RANGE) {
            List<Position> calculatedPath = calculatePath(start, movements);
            if (calculatedPath.getLast().equals(end)) {
                return calculatedPath;
            }
        }
        throw new IllegalArgumentException("올바른 도착 지점이 아닙니다.");
    }

    @Override
    public boolean isMovable(List<Position> path, BoardInterface boardInterface) {
        return moveStrategy.isMovable(path, side, boardInterface);
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

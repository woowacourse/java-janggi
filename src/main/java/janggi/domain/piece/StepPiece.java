package janggi.domain.piece;

import janggi.domain.Movement;
import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.policy.RoutePolicy;

import java.util.ArrayList;
import java.util.List;

public abstract class StepPiece extends ActivePiece {
    private final List<List<Movement>> moveRange;

    public StepPiece(List<List<Movement>> moveRange, RoutePolicy routePolicy, Side side, PieceType pieceType) {
        super(routePolicy, side, pieceType);
        this.moveRange = moveRange;
    }

    @Override
    public List<Position> findRoute(Position start, Position end) {
        return moveRange.stream()
                .map(movements -> calculatePath(start, movements))
                .filter(path -> path.getLast().equals(end))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_DESTINATION_MESSAGE));
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

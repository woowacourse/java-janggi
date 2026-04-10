package janggi.domain.piece;

import janggi.domain.Movement;
import janggi.domain.PalaceTopology;
import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.policy.RoutePolicy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public abstract class LinearPiece extends ActivePiece {
    public LinearPiece(RoutePolicy routePolicy, PalaceTopology palaceTopology, Side side, PieceType pieceType) {
        super(routePolicy, palaceTopology, side, pieceType);
    }

    @Override
    public List<Position> findRoute(Position start, Position end) {
        boolean isVertical = start.isVertical(end);
        boolean isHorizon = start.isHorizon(end);
        if (isVertical || isHorizon) {
            return calculateStraightPath(start, end, isVertical);
        }
        return calculatePalacePath(start, end);
    }

    private List<Position> calculateStraightPath(Position start, Position end, boolean isVertical){
        int dist = start.calculateDistance(end, isVertical);
        Movement movement = resolveMovement(isVertical, dist);
        return Stream.iterate(start, current -> current.move(movement))
                .limit(Math.abs(dist) + 1)
                .toList();
    }

    private List<Position> calculatePalacePath(Position start, Position end) {
        return palaceTopology.diagonalLineMovements(start).stream()
                .map(movements -> calculateMovementPath(start, movements))
                .filter(path -> path.getLast().equals(end))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_DESTINATION_MESSAGE));
    }

    private List<Position> calculateMovementPath(Position start, List<Movement> movements) {
        List<Position> path = new ArrayList<>(List.of(start));
        for (Movement movement : movements) {
            Position step = path.getLast().move(movement);
            path.add(step);
        }
        return path;
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

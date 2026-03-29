package janggi.domain.piece;

import janggi.domain.Movement;
import janggi.domain.Position;
import janggi.domain.Route;
import janggi.domain.Side;
import janggi.domain.board.BoardInterface;
import janggi.domain.policy.ClearPathPolicy;
import java.util.ArrayList;
import java.util.List;

public class Pawn extends ActivePiece {
    private final List<List<Movement>> MOVE_RANGE;

    public Pawn(Side side, List<List<Movement>> moveRange) {
        super(new ClearPathPolicy(), side, PieceType.PAWN);
        MOVE_RANGE = moveRange;
    }

    public static Pawn from(Side side) {
        List<List<Movement>> moveRange = new ArrayList<>(List.of(List.of(Movement.LEFT), List.of(Movement.RIGHT)));
        moveRange.add(calculateForwardMovement(side));
        return new Pawn(side, moveRange);
    }

    private static List<Movement> calculateForwardMovement(Side side) {
        if (side.equals(Side.CHO)) {
            return List.of(Movement.UP);
        }
        return List.of(Movement.DOWN);
    }

    @Override
    public Route findRoute(Position start, Position end) {
        for (List<Movement> movements : MOVE_RANGE) {
            Route calculatedPath = calculatePath(start, movements);
            if (calculatedPath.isArrivalPoint(end)) {
                return calculatedPath;
            }
        }
        throw new IllegalArgumentException(INVALID_DESTINATION_MESSAGE);
    }

    @Override
    public void validateRoute(Route route, BoardInterface boardInterface) {
        if (!routePolicy.isMovable(route, side, boardInterface)) {
            throw new IllegalArgumentException(UNMOVABLE_ROUTE_MESSAGE);
        }
    }

    private Route calculatePath(Position start, List<Movement> path) {
        List<Position> calculatedPath = new ArrayList<>(List.of(start));
        path.forEach(movement -> calculatedPath.add(calculatedPath.getLast().move(movement)));
        return new Route(calculatedPath);
    }
}

package janggi.domain.piece;

import janggi.domain.Movement;
import janggi.domain.Movements;
import janggi.domain.Position;
import janggi.domain.Route;
import janggi.domain.Side;
import janggi.domain.board.BoardInterface;
import janggi.domain.policy.ClearPathPolicy;
import java.util.ArrayList;
import java.util.List;

public class Pawn extends ActivePiece {
    private final List<Movements> MOVE_RANGE;

    public Pawn(Side side, List<Movements> moveRange) {
        super(new ClearPathPolicy(), side, PieceType.PAWN);
        MOVE_RANGE = moveRange;
    }

    public static Pawn from(Side side) {
        List<Movements> moveRange = new ArrayList<>(List.of(new Movements(List.of(Movement.LEFT)), new Movements(List.of(Movement.RIGHT))));
        moveRange.add(calculateForwardMovement(side));
        return new Pawn(side, moveRange);
    }

    private static Movements calculateForwardMovement(Side side) {
        if (side.equals(Side.CHO)) {
            return new Movements(List.of(Movement.UP));
        }
        return new Movements(List.of(Movement.DOWN));
    }

    @Override
    public Route findRoute(Position start, Position end) {
        for (Movements movements : MOVE_RANGE) {
            Route calculatedPath = movements.calculatePath(start);
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
}

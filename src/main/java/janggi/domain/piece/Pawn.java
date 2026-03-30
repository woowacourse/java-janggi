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
import java.util.Optional;

public class Pawn extends ActivePiece {
    private final List<Movements> moveRange;

    public Pawn(Side side, List<Movements> moveRange) {
        super(new ClearPathPolicy(), side, PieceType.PAWN);
        this.moveRange = moveRange;
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
        return moveRange.stream()
                .map(movements -> movements.calculatePath(start))
                .flatMap(Optional::stream)
                .filter(route -> route.isArrivalPoint(end))
                .findFirst().orElseThrow(() -> new IllegalArgumentException(INVALID_DESTINATION_MESSAGE));
    }

    @Override
    public void validateRoute(Route route, BoardInterface boardInterface) {
        if (!routePolicy.isMovable(route, side, boardInterface)) {
            throw new IllegalArgumentException(UNMOVABLE_ROUTE_MESSAGE);
        }
    }
}

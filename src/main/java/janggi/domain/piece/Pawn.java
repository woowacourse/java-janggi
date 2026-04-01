package janggi.domain.piece;

import janggi.domain.Movement;
import janggi.domain.Position;
import janggi.domain.Route;
import janggi.domain.Side;
import java.util.Map;
import java.util.Set;

public class Pawn extends SingleLinearPiece {
    private Map<Side, Set<Movement>> isBackward = Map.of(
            Side.HAN, Set.of(Movement.UP, Movement.UP_LEFT, Movement.UP_RIGHT),
            Side.CHO, Set.of(Movement.DOWN, Movement.DOWN_LEFT, Movement.DOWN_RIGHT)
    );

    public Pawn(Side side) {
        super(side, PieceType.PAWN);
    }


    @Override
    public Route findRoute(Position start, Position end) {
        try {
            Movement direction = start.getLinearDirection(end);
            validateDirection(direction);

            int distance = start.calculateLinearDistance(end);

            return calculatePath(start, direction, distance);
        } catch (IllegalStateException e) {
            throw new IllegalArgumentException(INVALID_DESTINATION_MESSAGE);
        }
    }

    private void validateDirection(Movement direction) {
        if(isBackward.get(side).contains(direction)) {
            throw new IllegalArgumentException(INVALID_DESTINATION_MESSAGE);
        }
    }
}

package janggi.domain.piece;

import janggi.domain.Movement;
import janggi.domain.Position;
import janggi.domain.Side;
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
    public List<Position> findRoute(Position start, Position end) {
        for (List<Movement> movements : MOVE_RANGE) {
            List<Position> calculatedPath = calculatePath(start, movements);
            if (calculatedPath.getLast().equals(end)) {
                return calculatedPath;
            }
        }
        throw new IllegalArgumentException(INVALID_DESTINATION_MESSAGE);
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

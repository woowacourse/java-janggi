package janggi.domain.piece;

import janggi.domain.Movement;
import janggi.domain.Side;
import janggi.domain.policy.ClearPathPolicy;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends StepPiece {
    public Pawn(Side side, List<List<Movement>> moveRange) {
        super(moveRange, new ClearPathPolicy(), side, PieceType.PAWN);
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
}

package janggi.domain.piece;

import janggi.domain.Movement;
import janggi.domain.Movements;
import janggi.domain.Side;
import janggi.domain.policy.ClearPathPolicy;
import java.util.ArrayList;
import java.util.List;

public class Pawn extends StepPiece {
    public Pawn(Side side, List<Movements> moveRange) {
        super(moveRange, new ClearPathPolicy(), side, PieceType.PAWN);
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
}

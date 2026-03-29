package janggi.domain.piece;

import janggi.domain.Movement;
import janggi.domain.Side;
import janggi.domain.policy.ClearPathPolicy;
import java.util.List;

public class Sa extends StepPiece {
    private static final List<List<Movement>> MOVE_RANGE = List.of(
            List.of(Movement.UP),
            List.of(Movement.DOWN),
            List.of(Movement.LEFT),
            List.of(Movement.RIGHT)
    );

    public Sa(Side side) {
        super(MOVE_RANGE, new ClearPathPolicy(), side, PieceType.SA);
    }
}

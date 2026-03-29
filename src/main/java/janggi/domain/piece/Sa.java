package janggi.domain.piece;

import janggi.domain.Movement;
import janggi.domain.Movements;
import janggi.domain.Side;
import janggi.domain.policy.ClearPathPolicy;
import java.util.List;

public class Sa extends StepPiece {
    private static final List<Movements> MOVE_RANGE = List.of(
            new Movements(List.of(Movement.UP)),
            new Movements(List.of(Movement.DOWN)),
            new Movements(List.of(Movement.LEFT)),
            new Movements(List.of(Movement.RIGHT))
    );

    public Sa(Side side) {
        super(MOVE_RANGE, new ClearPathPolicy(), side, PieceType.SA);
    }
}

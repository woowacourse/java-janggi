package janggi.domain.piece;

import janggi.domain.Movement;
import janggi.domain.Movements;
import janggi.domain.Side;
import janggi.domain.policy.ClearPathPolicy;
import java.util.List;

public class Ma extends StepPiece {
    private static final List<Movements> MOVE_RANGE = List.of(
            new Movements(List.of(Movement.UP, Movement.UP_LEFT)),
            new Movements(List.of(Movement.UP, Movement.UP_RIGHT)),
            new Movements(List.of(Movement.RIGHT, Movement.UP_RIGHT)),
            new Movements(List.of(Movement.RIGHT, Movement.DOWN_RIGHT)),
            new Movements(List.of(Movement.DOWN, Movement.DOWN_RIGHT)),
            new Movements(List.of(Movement.DOWN, Movement.DOWN_LEFT)),
            new Movements(List.of(Movement.LEFT, Movement.UP_LEFT)),
            new Movements(List.of(Movement.LEFT, Movement.DOWN_LEFT))
    );

    public Ma(Side side) {
        super(MOVE_RANGE, new ClearPathPolicy(), side, PieceType.MA);
    }
}

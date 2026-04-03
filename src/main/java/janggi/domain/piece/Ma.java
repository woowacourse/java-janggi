package janggi.domain.piece;

import janggi.domain.Movement;
import janggi.domain.PalaceTopology;
import janggi.domain.Side;
import janggi.domain.policy.ClearPathPolicy;

import java.util.List;

public class Ma extends StepPiece {
    private static final List<List<Movement>> MOVE_RANGE = List.of(
            List.of(Movement.UP, Movement.UP_LEFT),
            List.of(Movement.UP, Movement.UP_RIGHT),
            List.of(Movement.RIGHT, Movement.UP_RIGHT),
            List.of(Movement.RIGHT, Movement.DOWN_RIGHT),
            List.of(Movement.DOWN, Movement.DOWN_RIGHT),
            List.of(Movement.DOWN, Movement.DOWN_LEFT),
            List.of(Movement.LEFT, Movement.UP_LEFT),
            List.of(Movement.LEFT, Movement.DOWN_LEFT)
    );

    public Ma(Side side, PalaceTopology palaceTopology) {
        super(MOVE_RANGE, new ClearPathPolicy(), palaceTopology, side, PieceType.MA);
    }
}

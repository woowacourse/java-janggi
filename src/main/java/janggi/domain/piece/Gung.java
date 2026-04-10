package janggi.domain.piece;

import janggi.domain.Movement;
import janggi.domain.PalaceTopology;
import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.policy.ClearPathPolicy;

import java.util.List;

public class Gung extends StepPiece {
    private static final String GUNG_MOVE_OUTSIDE_PALACE_MESSAGE = "궁은 궁성 밖으로 나갈 수 없습니다.";
    private static final List<List<Movement>> MOVE_RANGE = List.of(
            List.of(Movement.UP),
            List.of(Movement.DOWN),
            List.of(Movement.LEFT),
            List.of(Movement.RIGHT)
    );

    public Gung(Side side, PalaceTopology palaceTopology) {
        super(MOVE_RANGE, new ClearPathPolicy(), palaceTopology, side, PieceType.GUNG);
    }

    @Override
    public List<Position> findRoute(Position start, Position end) {
        validatePalace(end, GUNG_MOVE_OUTSIDE_PALACE_MESSAGE);
        return super.findRoute(start, end);
    }
}
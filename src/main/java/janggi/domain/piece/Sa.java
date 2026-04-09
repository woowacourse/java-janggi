package janggi.domain.piece;

import janggi.domain.Movement;
import janggi.domain.PalaceTopology;
import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.policy.ClearPathPolicy;

import java.util.List;

public class Sa extends StepPiece {
    private static final String SA_MOVE_OUTSIDE_PALACE_MESSAGE = "사는 궁성 밖으로 나갈 수 없습니다.";
    private static final List<List<Movement>> MOVE_RANGE = List.of(
            List.of(Movement.UP),
            List.of(Movement.DOWN),
            List.of(Movement.LEFT),
            List.of(Movement.RIGHT)
    );

    public Sa(Side side, PalaceTopology palaceTopology) {
        super(MOVE_RANGE, new ClearPathPolicy(), palaceTopology, side, PieceType.SA);
    }

    @Override
    public List<Position> findRoute(Position start, Position end) {
        validatePalace(end);
        return super.findRoute(start, end);
    }

    private void validatePalace(Position position){
        if(!palaceTopology.isPalace(position)) {
            throw new IllegalArgumentException(SA_MOVE_OUTSIDE_PALACE_MESSAGE);
        }
    }
}

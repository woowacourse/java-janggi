package janggi.domain.piece;

import janggi.domain.Movement;
import janggi.domain.PalaceTopology;
import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.policy.ClearPathPolicy;

import java.util.List;

public class Pawn extends StepPiece {
    private static final String PAWN_MOVE_BACKWARD_MESSAGE = "병은 뒤로 이동할 수 없습니다.";
    private static final List<List<Movement>> MOVE_RANGE = List.of(
            List.of(Movement.UP),
            List.of(Movement.DOWN),
            List.of(Movement.LEFT),
            List.of(Movement.RIGHT)
    );

    public Pawn(Side side, PalaceTopology palaceTopology) {
        super(MOVE_RANGE, new ClearPathPolicy(), palaceTopology, side, PieceType.PAWN);
    }

    @Override
    public List<Position> findRoute(Position start, Position end) {
        validateNotBackwardMove(start, end);
        return super.findRoute(start, end);
    }

    private void validateNotBackwardMove(Position start, Position end){
        if (side.equals(Side.CHO) && start.isBelow(end)) {
            throw new IllegalArgumentException(PAWN_MOVE_BACKWARD_MESSAGE);
        }
        if(side.equals(Side.HAN) && start.isAbove(end)) {
            throw new IllegalArgumentException(PAWN_MOVE_BACKWARD_MESSAGE);
        }
    }
}

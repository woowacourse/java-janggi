package janggi.domain.piece;

import janggi.domain.Movement;
import janggi.domain.PalaceRoutes;
import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.policy.ClearPathPolicy;

import java.util.ArrayList;
import java.util.List;

public class Gung extends StepPiece {
    private static final String GUNG_MOVE_OUTSIDE_PALACE_MESSAGE = "궁은 궁성 밖으로 나갈 수 없습니다.";
    private static final List<List<Movement>> MOVE_RANGE = List.of(
            List.of(Movement.UP),
            List.of(Movement.DOWN),
            List.of(Movement.LEFT),
            List.of(Movement.RIGHT)
    );

    public Gung(Side side) {
        super(MOVE_RANGE, new ClearPathPolicy(), side, PieceType.GUNG);
    }

    @Override
    public List<Position> findRoute(Position start, Position end) {
        validatePalace(end);
        return super.findRoute(start, end);
    }

    private void validatePalace(Position position){
        if(!position.isPalace()) {
            throw new IllegalArgumentException(GUNG_MOVE_OUTSIDE_PALACE_MESSAGE);
        }
    }

    @Override
    protected List<List<Movement>> candidateMoves(Position position){
        List<List<Movement>> moves = new ArrayList<>(super.candidateMoves(position));
        moves.addAll(PalaceRoutes.diagonalOneStepMovements(position));
        return moves;
    }
}
package janggi.domain.piece;

import janggi.domain.Movement;
import janggi.domain.PalaceRoutes;
import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.policy.ClearPathPolicy;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends StepPiece {
    private static final String PAWN_MOVE_BACKWARD_MESSAGE = "병은 뒤로 이동할 수 없습니다.";
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

    @Override
    protected List<List<Movement>> candidateMoves(Position position){
        List<List<Movement>> moves = new ArrayList<>(super.candidateMoves(position));
        moves.addAll(PalaceRoutes.diagonalOneStepMovements(position));
        return moves;
    }
}

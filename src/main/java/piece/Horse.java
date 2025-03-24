package piece;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import board.Board;
import board.Position;

public class Horse extends Piece {

    private static final List<Movement> MOVEMENTS = List.of(
            Movement.TOP_LEFT_TOP,
            Movement.TOP_RIGHT_TOP,
            Movement.BOTTOM_LEFT_BOTTOM,
            Movement.BOTTOM_RIGHT_BOTTOM,
            Movement.LEFT_LEFT_TOP,
            Movement.LEFT_LEFT_BOTTOM,
            Movement.RIGHT_RIGHT_TOP
    );
    private static final int MOVEMENT_TOTAL_STEP = 2;

    public Horse(final Position position, final Team team) {
        super(position, team);
    }

    @Override
    protected Set<Position> getMovablePositions(final Board board) {
        Set<Position> movablePositions = new HashSet<>();
        for (Movement movement : MOVEMENTS) {
            addMovablePosition(board, movement, movablePositions);
        }
        return movablePositions;
    }

    private void addMovablePosition(final Board board, final Movement movement, final Set<Position> movablePositions) {
        Position beforeLastStepPosition = moveBeforeLastStep(board, movement);
        Position movableFinalPosition = movement.applyMovementLastStep(beforeLastStepPosition);
        if (movableFinalPosition.isInValidPosition() || board.isSameTeamPosition(team, movableFinalPosition)) {
            return;
        }
        movablePositions.add(movableFinalPosition);
    }

    private Position moveBeforeLastStep(final Board board, final Movement movement) {
        Position movePosition = position;
        for (int step = 1; step < MOVEMENT_TOTAL_STEP; step++) {
            movePosition = movement.applyMovementStep(step, movePosition);
            if (movePosition.isInValidPosition() || board.isExists(movePosition)) {
                break;
            }
        }
        return movePosition;
    }

    @Override
    public String getDisplayName() {
        return "마";
    }

}

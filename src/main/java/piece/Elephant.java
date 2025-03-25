package piece;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import board.Board;
import board.Position;

public class Elephant extends Piece {

    private static final List<Movement> MOVEMENTS = List.of(
            Movement.TOP_LEFT_TOP_LEFT_TOP,
            Movement.TOP_RIGHT_TOP_RIGHT_TOP,
            Movement.BOTTOM_LEFT_BOTTOM_LEFT_BOTTOM,
            Movement.BOTTOM_RIGHT_BOTTOM_RIGHT_BOTTOM,
            Movement.LEFT_LEFT_TOP_LEFT_TOP,
            Movement.LEFT_LEFT_BOTTOM_LEFT_BOTTOM,
            Movement.RIGHT_RIGHT_TOP_RIGHT_TOP,
            Movement.RIGHT_RIGHT_BOTTOM_RIGHT_BOTTOM
    );

    private static final int MOVEMENT_TOTAL_STEP = 3;

    public Elephant(final Position position, final Team team) {
        super(position, team);
    }

    @Override
    protected Set<Position> getMovablePositions(final Board board) {
        Set<Position> movablePositions = new HashSet<>();
        Map<Movement, Position> beforeLastStepPositions = moveBeforeLastStep(board);
        if (beforeLastStepPositions.isEmpty()) {
            throw new IllegalArgumentException("현재 해당 기물이 이동 가능한 곳이 없습니다.");
        }
        addMovablePosition(board, movablePositions, beforeLastStepPositions);
        return movablePositions;
    }

    private Map<Movement, Position> moveBeforeLastStep(final Board board) {
        Map<Movement, Position> moveBeforeLastStepPositions = new EnumMap<>(Movement.class);
        for (Movement movement : MOVEMENTS) {
            addBeforeLastStepPosition(board, movement, moveBeforeLastStepPositions);
        }
        return moveBeforeLastStepPositions;
    }

    private void addBeforeLastStepPosition(final Board board, final Movement movement,
                                           final Map<Movement, Position> moveBeforeLastStepPositions
    ) {
        Position movePosition = position;
        for (int step = 1; step < MOVEMENT_TOTAL_STEP; step++) {
            movePosition = movement.applyMovementStep(step, movePosition);
            if (movePosition.isInValidPosition() || board.isExists(movePosition)) {
                break;
            }
            if (step == (MOVEMENT_TOTAL_STEP - 1)) {
                moveBeforeLastStepPositions.put(movement, movePosition);
            }
        }
    }

    private void addMovablePosition(final Board board, final Set<Position> movablePositions,
                                    final Map<Movement, Position> beforeLastStepPositions
    ) {
        for (Movement movement : beforeLastStepPositions.keySet()) {
            Position beforeLastStepPosition = beforeLastStepPositions.get(movement);
            Position movableFinalPosition = movement.applyMovementLastStep(beforeLastStepPosition);
            if (movableFinalPosition.isInValidPosition() || board.isSameTeamPosition(team, movableFinalPosition)) {
                continue;
            }
            movablePositions.add(movableFinalPosition);
        }
    }

    @Override
    public String getDisplayName() {
        return "상";
    }

}

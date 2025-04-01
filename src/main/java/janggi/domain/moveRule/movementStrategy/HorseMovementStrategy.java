package janggi.domain.moveRule.movementStrategy;

import janggi.domain.board.Direction;
import janggi.domain.board.Movement;
import janggi.domain.board.PiecePath;
import janggi.domain.board.Position;
import janggi.domain.piece.TeamColor;
import java.util.List;

public class HorseMovementStrategy implements MovementStrategy {
    private static final int HORSE_SHORT_STEP = 1;
    private static final int HORSE_LONG_STEP = 2;

    @Override
    public boolean isValidMovement(PiecePath path, TeamColor teamColor) {
        return path.matchesMovementStep(HORSE_LONG_STEP, HORSE_SHORT_STEP);
    }

    @Override
    public List<Position> findAllIntermediatePositions(PiecePath path) {
        Direction direction = calculateLeadingStraightStep(path.rowDifference(), path.columnDifference());
        return path.tracePositionsByDirection(Movement.from(direction));
    }

    private Direction calculateLeadingStraightStep(int rowDifference, int columnDifference) {
        int rowStep = rowDifference / HORSE_LONG_STEP;
        int columnStep = columnDifference / HORSE_LONG_STEP;
        return Direction.from(rowStep, columnStep);
    }
}

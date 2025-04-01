package janggi.domain.moveRule.movementStrategy;

import janggi.domain.board.Direction;
import janggi.domain.board.Movement;
import janggi.domain.board.PiecePath;
import janggi.domain.board.Position;
import janggi.domain.piece.TeamColor;
import java.util.List;

public class ElephantMovementStrategy implements MovementStrategy {
    private static final int ELEPHANT_SHORT_STEP = 2;
    private static final int ELEPHANT_LONG_STEP = 3;

    @Override
    public boolean isValidMovement(PiecePath path, TeamColor teamColor) {
        return path.matchesMovementStep(ELEPHANT_LONG_STEP, ELEPHANT_SHORT_STEP);
    }

    @Override
    public List<Position> findAllIntermediatePositions(PiecePath path) {
        int rowDifference = path.rowDifference();
        int columnDifference = path.columnDifference();

        Direction firstDirection = calculateLeadingStraightStep(rowDifference, columnDifference);
        Direction secondDirection = calculateDiagonalStepToTarget(rowDifference, columnDifference);

        return path.tracePositionsByDirection(Movement.from(firstDirection, secondDirection));
    }

    private Direction calculateLeadingStraightStep(int rowDifference, int columnDifference) {
        int rowStep = rowDifference / ELEPHANT_LONG_STEP;
        int columnStep = columnDifference / ELEPHANT_LONG_STEP;
        return Direction.from(rowStep, columnStep);
    }

    private Direction calculateDiagonalStepToTarget(int rowDifference, int columnDifference) {
        int rowStep = rowDifference / Math.abs(rowDifference);
        int columnStep = columnDifference / Math.abs(columnDifference);
        return Direction.from(rowStep, columnStep);
    }
}

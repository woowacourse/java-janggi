package janggi.domain.piece.strategy;

import janggi.domain.Position;
import janggi.domain.piece.Camp;
import janggi.exception.ExceptionMessage;
import java.util.ArrayList;
import java.util.List;

public class DiagonalStepStrategy implements MoveStrategy {

    private static final int STRAIGHT_DISTANCE = 1;

    private final int diagonalDistance;

    public DiagonalStepStrategy(int diagonalDistance) {
        this.diagonalDistance = diagonalDistance;
    }

    @Override
    public List<Position> findPath(Position source, Position destination, Camp camp) {
        DirectionInformation direction = new DirectionInformation(source, destination);
        validateMovement(direction);

        Position first = moveStraightStep(source, direction);

        List<Position> path = new ArrayList<>();
        path.add(first);
        path.addAll(moveDiagonal(first, direction));

        return path;
    }

    private void validateMovement(DirectionInformation direction) {
        if (!direction.hasAbsDifferences(diagonalDistance, STRAIGHT_DISTANCE + diagonalDistance)) {
            throw new IllegalArgumentException(
                    ExceptionMessage.INVALID_DIAGONAL_STEP_MOVE.getMessage(STRAIGHT_DISTANCE, diagonalDistance)
            );
        }
    }

    private Position moveStraightStep(Position source, DirectionInformation direction) {
        if (direction.isRowBiggerThanCol()) {
            return source.moveRow(direction.calculateRowDirection());
        }
        return source.moveCol(direction.calculateColDirection());
    }

    private List<Position> moveDiagonal(Position source, DirectionInformation direction) {
        List<Position> path = new ArrayList<>();
        int rowDirection = direction.calculateRowDirection();
        int colDirection = direction.calculateColDirection();

        for (int i = 0; i < diagonalDistance; i++) {
            source = source.moveDiagonal(rowDirection, colDirection);
            path.add(source);
        }
        return path;
    }
}

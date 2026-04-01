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
        DirectionInformation directionInfo = new DirectionInformation(source, destination);

        validateMovement(directionInfo);

        if (directionInfo.isRowBiggerThanCol()) {
            return createRowFirstPath(source, directionInfo);
        }
        return createColFirstPath(source, directionInfo);
    }

    private void validateMovement(DirectionInformation directionInfo) {
        int absRowDiff = directionInfo.calculateAbsRowDifference();
        int absColDiff = directionInfo.calculateAbsColDifference();
        int longDistance = STRAIGHT_DISTANCE + diagonalDistance;
        int shortDistance = diagonalDistance;

        boolean isValid = (absRowDiff == shortDistance && absColDiff == longDistance)
                || (absRowDiff == longDistance && absColDiff == shortDistance);

        if (!isValid) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_DIAGONAL_STEP_MOVE.getMessage(STRAIGHT_DISTANCE, diagonalDistance));
        }
    }

    private List<Position> createRowFirstPath(Position source, DirectionInformation directionInfo) {
        List<Position> path = new ArrayList<>();
        source = source.moveRow(directionInfo.calculateRowDirection());
        path.add(source);
        path.addAll(moveDiagonal(source, directionInfo));
        return path;
    }

    private List<Position> createColFirstPath(Position source, DirectionInformation directionInfo) {
        List<Position> path = new ArrayList<>();
        source = source.moveCol(directionInfo.calculateColDirection());
        path.add(source);
        path.addAll(moveDiagonal(source, directionInfo));
        return path;
    }

    private List<Position> moveDiagonal(Position source, DirectionInformation directionInfo) {
        List<Position> path = new ArrayList<>();
        for (int i = 0; i < diagonalDistance; i++) {
            source = source.moveDiagonal(directionInfo.calculateRowDirection(), directionInfo.calculateColDirection());
            path.add(source);
        }
        return path;
    }
}
